package ragalik.baraxolka.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_reports.*
import ragalik.baraxolka.R
import ragalik.baraxolka.feed.viewmodel.ReportsViewModel
import ragalik.baraxolka.view.adapter.ReportsAdapter

class ReportsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isReloaded = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reportsProgressBar = view.findViewById(R.id.progress_reports)
        if (isReloaded) {
            reportsProgressBar.visibility = View.GONE
        } else {
            reportsProgressBar.visibility = View.VISIBLE
        }

        supportFragment = activity?.supportFragmentManager!!

        refresherReports!!.setOnRefreshListener {
            itemViewModel?.liveDataSource?.value?.invalidate()
            refresherReports!!.isRefreshing = false
            reportsProgressBar.visibility = View.VISIBLE
        }

        getReports()
    }

    companion object {
        var itemViewModel: ReportsViewModel? = null
        private var isReloaded = false
        lateinit var reportsProgressBar : ProgressBar
        lateinit var supportFragment : FragmentManager
    }

    private fun getReports() {
        val reportsAdapter = ReportsAdapter("REPORTS")
        ReportsRecyclerView?.layoutManager = LinearLayoutManager(activity)
        itemViewModel = ViewModelProvider(this).get(ReportsViewModel::class.java)
        itemViewModel!!.reportsPagedList.observe(viewLifecycleOwner, { reportsAdapter.submitList(it) })
        ReportsRecyclerView?.adapter = reportsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isReloaded = true
    }
}