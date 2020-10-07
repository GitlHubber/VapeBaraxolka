package ragalik.baraxolka.view.ui.fragment.my_ads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.feed.factory.ModelFactory
import ragalik.baraxolka.feed.viewmodel.MyAdsViewModel
import ragalik.baraxolka.view.adapter.AdAdapter

class MyRejectedAds : Fragment() {

    private var isReloaded = false
    private lateinit var rejectedRecyclerView: RecyclerView
    private lateinit var rejected_adapter: AdAdapter

    companion object {
        lateinit var rejectedViewModel: MyAdsViewModel
        lateinit var pb_rejected: ProgressBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isReloaded = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rejected_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pb_rejected = view.findViewById(R.id.progress_rejected)
        if (isReloaded) {
            pb_rejected.visibility = View.GONE
        } else {
            pb_rejected.visibility = View.VISIBLE
        }
        rejectedRecyclerView = view.findViewById(R.id.RejectedAdsView)
        rejected_adapter = AdAdapter("REJECTED")
        rejectedRecyclerView.layoutManager = LinearLayoutManager(activity)
        rejectedViewModel = ViewModelProvider(this, ModelFactory(2, MainActivity.sp.getInt("id", 0), false)).get(MyAdsViewModel::class.java)
        rejectedViewModel.myAdsPagedList.observe(viewLifecycleOwner, { rejected_adapter.submitList(it) })
        rejectedRecyclerView.adapter = rejected_adapter
        MyAdsFragment.getAdCount(2, MainActivity.sp.getInt("id", 0))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isReloaded = true
    }
}