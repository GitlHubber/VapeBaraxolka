package ragalik.baraxolka.view.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.feed.viewmodel.AdModeratorViewModel
import ragalik.baraxolka.utils.APP_ACTIVITY
import ragalik.baraxolka.view.adapter.AdAdapter


class AdModeratorFragment : BaseFragment(R.layout.fragment_ad_moderator) {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adModeratorView: RecyclerView
    private var isReloaded = false

    companion object {
        lateinit var context: Context
        lateinit var itemViewModel: AdModeratorViewModel
        lateinit var progressBar: ProgressBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isReloaded = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        MainActivity.isActualFragment = false
        MainActivity.invalidateSearchMenu()
        progressBar = view.findViewById(R.id.progress_moderator)
        if (isReloaded) {
            progressBar.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
        }

//        Toolbar toolbar = view.findViewById(R.id.adModeratorToolbar);
//        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
//        if (appCompatActivity != null) {
//            appCompatActivity.setSupportActionBar(toolbar);
//            toolbar.setTitle("Объявления модератора");
//        }
        APP_ACTIVITY.mToolbar.title = "Объявления модератора"
        Companion.context = context!!
        swipeRefreshLayout = view.findViewById(R.id.refresherModerator)
        swipeRefreshLayout.setOnRefreshListener {
            itemViewModel.liveDataSource.value!!.invalidate()
            swipeRefreshLayout.isRefreshing = false
            progressBar.visibility = View.VISIBLE
        }
        adModeratorView = view.findViewById(R.id.ModeratorAdsView)
        getAds()
        MainActivity.fab.hide()
    }

    private fun getAds() {
        val adModeratorAdapter = AdAdapter("MODERATOR")
        adModeratorView.layoutManager = LinearLayoutManager(activity)
        itemViewModel = ViewModelProvider(this).get(AdModeratorViewModel::class.java)
        itemViewModel.adModeratorPagedList.observe(viewLifecycleOwner, { adModeratorAdapter.submitList(it) })
        adModeratorView.adapter = adModeratorAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isReloaded = true
    }
}