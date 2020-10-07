package ragalik.baraxolka.view.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.feed.viewmodel.FavouritesViewModel
import ragalik.baraxolka.utils.APP_ACTIVITY
import ragalik.baraxolka.view.adapter.AdAdapter


class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private lateinit var favouritesRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var itemViewModel: FavouritesViewModel
    private var isReloaded = false

    companion object {
        lateinit var progressBar: ProgressBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isReloaded = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_favourites)
        if (isReloaded) {
            progressBar.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
        }
        MainActivity.isActualFragment = false
        MainActivity.invalidateSearchMenu()
        //        Toolbar toolbar = view.findViewById(R.id.toolbar_favourites);
//        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
//        if (appCompatActivity != null) {
//            appCompatActivity.setSupportActionBar(toolbar);
//            toolbar.setTitle("Закладки");
//        }
        APP_ACTIVITY.mToolbar.title = "Закладки"

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        MainActivity.drawer.addDrawerListener(toggle);
//        toggle.syncState();
        MainActivity.fab.hide()
        swipeRefreshLayout = view.findViewById(R.id.refresherFavourites)
        swipeRefreshLayout.setOnRefreshListener {
            itemViewModel.liveDataSource.value!!.invalidate()
            swipeRefreshLayout.isRefreshing = false
            progressBar.visibility = View.VISIBLE
        }
        favouritesRecyclerView = view.findViewById(R.id.favouritesRecyclerView)
        getFavourites()
    }

    private fun getFavourites() {
        val favouritesAdapter = AdAdapter("FAVOURITES")
        favouritesRecyclerView.layoutManager = LinearLayoutManager(activity)

        itemViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
        itemViewModel.favouritesPagedList.observe(viewLifecycleOwner, { favouritesAdapter.submitList(it) })

        favouritesRecyclerView.adapter = favouritesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isReloaded = true
    }
}