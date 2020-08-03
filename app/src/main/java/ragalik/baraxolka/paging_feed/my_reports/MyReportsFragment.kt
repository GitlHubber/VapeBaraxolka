package ragalik.baraxolka.paging_feed.my_reports

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


class MyReportsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_my_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Slidr.attach(this)

        MainActivity.isActualFragment = false
        MainActivity.invalidateSearchMenu()

        supportFragment = activity?.supportFragmentManager!!

        (activity as AppCompatActivity).setSupportActionBar(myReportsToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myReportsToolbar.title = "Мои жалобы"

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
        itemViewModel!!.myReportsPagedList.observe(viewLifecycleOwner, Observer { myReportsAdapter.submitList(it) })
        MyReportsRecyclerView?.adapter = myReportsAdapter
    }
}