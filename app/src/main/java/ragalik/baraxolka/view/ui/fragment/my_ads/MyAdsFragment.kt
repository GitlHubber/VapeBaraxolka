package ragalik.baraxolka.view.ui.fragment.my_ads

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.IApi
import ragalik.baraxolka.network.entities.AdsCount
import ragalik.baraxolka.utils.APP_ACTIVITY
import ragalik.baraxolka.view.ui.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAdsFragment : BaseFragment(R.layout.fragment_my_ads), OnRefreshListener {

    private lateinit var mPager: ViewPager
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.isActualFragment = false
        MainActivity.invalidateSearchMenu()
        APP_ACTIVITY.mToolbar.title = "Мои объявления"
        MainActivity.fab.hide()

        mPager = view.findViewById<View>(R.id.MY_ADS_View_Pager) as ViewPager
        mPager.offscreenPageLimit = 4
        mPager.adapter = MyAdsPagerAdapter(childFragmentManager)

        mTab_layout = view.findViewById<View>(R.id.MY_ADS_Tab_Layout) as TabLayout
        mTab_layout.setupWithViewPager(mPager)

        refreshLayout = view.findViewById(R.id.myAdsRefresher)
        refreshLayout.setOnRefreshListener(this)

        apiClient = ApiClient.getApi()

        activeCount = 0
        rejectedCount = 0
        onModerateCount = 0
        nonActiveCount = 0
    }

    internal inner class MyAdsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val childFragments: Array<Fragment> = arrayOf(
                MyActiveAds(),
                MyOnModerateAds(),
                MyRejectedAds(),
                MyNonActiveAds()
        )

        override fun getItem(position: Int): Fragment {
            return childFragments[position]
        }

        override fun getCount(): Int {
            return childFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return TABS[position]
        }

    }

    override fun onRefresh() {
        when (mPager.currentItem) {
            0 -> {
                getAdCount(1, MainActivity.sp.getInt("id", 0))
                MyActiveAds.activeViewModel.liveDataSource.value!!.invalidate()
                refreshLayout.isRefreshing = false
                MyActiveAds.pb_active.visibility = View.VISIBLE
            }
            1 -> {
                getAdCount(3, MainActivity.sp.getInt("id", 0))
                MyOnModerateAds.onModerateViewModel.liveDataSource.value!!.invalidate()
                refreshLayout.isRefreshing = false
                MyOnModerateAds.pb_on_moderate.visibility = View.VISIBLE
                getAdCount(2, MainActivity.sp.getInt("id", 0))
                MyRejectedAds.rejectedViewModel.liveDataSource.value!!.invalidate()
                refreshLayout.isRefreshing = false
                MyRejectedAds.pb_rejected.visibility = View.VISIBLE
                getAdCount(4, MainActivity.sp.getInt("id", 0))
                MyNonActiveAds.nonActiveViewModel.liveDataSource.value!!.invalidate()
                refreshLayout.isRefreshing = false
                MyNonActiveAds.pb_non_active.visibility = View.VISIBLE
            }
            2 -> {
                getAdCount(2, MainActivity.sp.getInt("id", 0))
                MyRejectedAds.rejectedViewModel.liveDataSource.value!!.invalidate()
                refreshLayout.isRefreshing = false
                MyRejectedAds.pb_rejected.visibility = View.VISIBLE
                getAdCount(4, MainActivity.sp.getInt("id", 0))
                MyNonActiveAds.nonActiveViewModel.liveDataSource.value!!.invalidate()
                refreshLayout.isRefreshing = false
                MyNonActiveAds.pb_non_active.visibility = View.VISIBLE
            }
            3 -> {
                getAdCount(4, MainActivity.sp.getInt("id", 0))
                MyNonActiveAds.nonActiveViewModel.liveDataSource.value!!.invalidate()
                refreshLayout.isRefreshing = false
                MyNonActiveAds.pb_non_active.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        lateinit var mTab_layout: TabLayout
        private lateinit var apiClient: IApi

        private var activeCount = 0
        private var onModerateCount = 0
        private var rejectedCount = 0
        private var nonActiveCount = 0

        var TABS = arrayOf("0\nАктивные", "0\nНа модерации", "0\nОтклоненные", "0\nНеактивные")

        fun getAdCount(status: Int, id: Int) {
            apiClient.getMyAdsCount(status, id).enqueue(object : Callback<AdsCount?> {
                override fun onResponse(call: Call<AdsCount?>, response: Response<AdsCount?>) {
                    if (response.body() != null) {
                        when (status) {
                            1 -> activeCount = response.body()!!.count!!
                            2 -> rejectedCount = response.body()!!.count!!
                            3 -> onModerateCount = response.body()!!.count!!
                            4 -> nonActiveCount = response.body()!!.count!!
                        }
                    }
                    when (status) {
                        1 -> {
                            mTab_layout.getTabAt(0)!!.text = """
                                        $activeCount
                                        Активные
                                        """.trimIndent()
                        }
                        3 -> {
                            mTab_layout.getTabAt(1)!!.text = """
                                        $onModerateCount
                                        На модерации
                                        """.trimIndent()
                        }
                        2 -> {
                            mTab_layout.getTabAt(2)!!.text = """
                                        $rejectedCount
                                        Отклоненные
                                        """.trimIndent()
                        }
                        4 -> {
                            mTab_layout.getTabAt(3)!!.text = """
                                        $nonActiveCount
                                        Неактивные
                                        """.trimIndent()
                        }
                    }
                }

                override fun onFailure(call: Call<AdsCount?>, t: Throwable) {}
            })
        }
    }
}