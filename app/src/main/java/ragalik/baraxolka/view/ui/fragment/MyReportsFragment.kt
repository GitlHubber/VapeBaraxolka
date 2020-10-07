package ragalik.baraxolka.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_my_reports.*
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.feed.viewmodel.MyReportsViewModel
import ragalik.baraxolka.utils.APP_ACTIVITY
import ragalik.baraxolka.view.adapter.ReportsAdapter


class MyReportsFragment : BaseFragment(R.layout.fragment_my_reports) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Slidr.attach(this)

        MainActivity.isActualFragment = false
        MainActivity.invalidateSearchMenu()
        MainActivity.fab.hide()

        supportFragment = activity?.supportFragmentManager!!
        APP_ACTIVITY.mToolbar.title = "Мои жалобы"

//        (activity as AppCompatActivity).setSupportActionBar(myReportsToolbar)
//        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        myReportsToolbar.title = "Мои жалобы"

        myReportsProgressBar = view.findViewById(R.id.progress_my_reports)

        myReportsProgressBar.visibility = View.VISIBLE

        refresherMyReports!!.setOnRefreshListener {
            itemViewModel?.liveDataSource?.value?.invalidate()
            refresherMyReports!!.isRefreshing = false
            myReportsProgressBar.visibility = View.VISIBLE
        }

        getMyReports()
    }

    companion object {
        var itemViewModel: MyReportsViewModel? = null
        lateinit var myReportsProgressBar : ProgressBar
        lateinit var activity : AppCompatActivity
        lateinit var supportFragment : FragmentManager
    }

    private fun getMyReports () {
        val myReportsAdapter = ReportsAdapter("MY_REPORTS")
        MyReportsRecyclerView?.layoutManager = LinearLayoutManager(context)
        itemViewModel = ViewModelProvider(this).get(MyReportsViewModel::class.java)
        itemViewModel!!.myReportsPagedList.observe(viewLifecycleOwner, { myReportsAdapter.submitList(it) })
        MyReportsRecyclerView?.adapter = myReportsAdapter
    }
}