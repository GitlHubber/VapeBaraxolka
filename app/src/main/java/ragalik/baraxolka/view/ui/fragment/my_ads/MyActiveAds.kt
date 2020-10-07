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

class MyActiveAds : Fragment() {

    private var isReloaded = false

    private lateinit var active_adapter: AdAdapter
    private lateinit var activeRecyclerView: RecyclerView

    companion object {
        lateinit var pb_active: ProgressBar
        lateinit var activeViewModel: MyAdsViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isReloaded = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.active_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pb_active = view.findViewById(R.id.progress_active)
        if (isReloaded) {
            pb_active.visibility = View.GONE
        } else {
            pb_active.visibility = View.VISIBLE
        }
        activeRecyclerView = view.findViewById(R.id.ActiveAdsView)
        active_adapter = AdAdapter("ACTIVE")
        activeRecyclerView.layoutManager = LinearLayoutManager(activity)
        activeViewModel = ViewModelProvider(this, ModelFactory(1, MainActivity.sp.getInt("id", 0), false)).get(MyAdsViewModel::class.java)
        activeViewModel.myAdsPagedList.observe(viewLifecycleOwner, { active_adapter.submitList(it) })
        activeRecyclerView.adapter = active_adapter
        MyAdsFragment.getAdCount(1, MainActivity.sp.getInt("id", 0))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isReloaded = true
    }
}