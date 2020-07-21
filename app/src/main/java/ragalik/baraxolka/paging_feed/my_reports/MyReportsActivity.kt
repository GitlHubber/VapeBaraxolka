package ragalik.baraxolka.paging_feed.my_reports

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_my_reports.*
import ragalik.baraxolka.R


class MyReportsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reports)

        Slidr.attach(this)

        activity = this
        supportFragment = supportFragmentManager

        setSupportActionBar(myReportsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        myReportsProgressBar = findViewById(R.id.progress_my_reports)

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
        val myReportsAdapter = MyReportsAdapter()
        MyReportsRecyclerView?.layoutManager = LinearLayoutManager(this)
        itemViewModel = ViewModelProvider(this).get(MyReportsViewModel::class.java)
        itemViewModel!!.myReportsPagedList.observe(this, Observer { myReportsAdapter.submitList(it) })
        MyReportsRecyclerView?.adapter = myReportsAdapter
    }
}