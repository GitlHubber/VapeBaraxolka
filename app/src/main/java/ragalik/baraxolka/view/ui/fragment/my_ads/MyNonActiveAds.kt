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

class MyNonActiveAds : Fragment() {

    private var isReloaded = false
    private lateinit var nonActiveRecyclerView: RecyclerView
    private lateinit var non_active_adapter: AdAdapter

    companion object {
        lateinit var pb_non_active: ProgressBar
        lateinit var nonActiveViewModel: MyAdsViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isReloaded = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.non_active_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pb_non_active = view.findViewById(R.id.progress_non_active)
        if (isReloaded) {
            pb_non_active.visibility = View.GONE
        } else {
            pb_non_active.visibility = View.VISIBLE
        }
        nonActiveRecyclerView = view.findViewById(R.id.NonActiveAdsView)
        non_active_adapter = AdAdapter("NON_ACTIVE")
        nonActiveRecyclerView.layoutManager = LinearLayoutManager(activity)
        nonActiveViewModel = ViewModelProvider(this, ModelFactory(4, MainActivity.sp.getInt("id", 0), false)).get(MyAdsViewModel::class.java)
        nonActiveViewModel.myAdsPagedList.observe(viewLifecycleOwner, { non_active_adapter.submitList(it) })
        nonActiveRecyclerView.adapter = non_active_adapter
        MyAdsFragment.getAdCount(4, MainActivity.sp.getInt("id", 0))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isReloaded = true
    }
}