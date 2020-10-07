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

class MyOnModerateAds : Fragment() {

    private var isReloaded = false
    private lateinit var on_moderate_adapter: AdAdapter
    private lateinit var onModerateRecyclerView: RecyclerView

    companion object {
        lateinit var pb_on_moderate: ProgressBar
        lateinit var onModerateViewModel: MyAdsViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isReloaded = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.on_moderate_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pb_on_moderate = view.findViewById(R.id.progress_on_moderate)
        if (isReloaded) {
            pb_on_moderate.visibility = View.GONE
        } else {
            pb_on_moderate.visibility = View.VISIBLE
        }
        onModerateRecyclerView = view.findViewById(R.id.OnModerateAdsView)
        on_moderate_adapter = AdAdapter("ON_MODERATE")
        onModerateRecyclerView.layoutManager = LinearLayoutManager(activity)
        onModerateViewModel = ViewModelProvider(this, ModelFactory(3, MainActivity.sp.getInt("id", 0), false)).get(MyAdsViewModel::class.java)
        onModerateViewModel.myAdsPagedList.observe(viewLifecycleOwner, { on_moderate_adapter.submitList(it) })
        onModerateRecyclerView.adapter = on_moderate_adapter
        MyAdsFragment.getAdCount(3, MainActivity.sp.getInt("id", 0))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isReloaded = true
    }
}