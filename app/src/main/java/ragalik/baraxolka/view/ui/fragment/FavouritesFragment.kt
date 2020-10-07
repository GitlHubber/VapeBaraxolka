package ragalik.baraxolka.view.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.feed.viewmodel.FavouritesViewModel;
import ragalik.baraxolka.utils.AppConstantsKt;
import ragalik.baraxolka.view.adapter.AdAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends BaseFragment {

    private RecyclerView favouritesRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FavouritesViewModel itemViewModel;
    public static ProgressBar progressBar;
    private boolean isReloaded;

    public FavouritesFragment() {
        super(R.layout.fragment_favourites);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isReloaded = false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_favourites);
        if (isReloaded) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }

        MainActivity.isActualFragment = false;
        MainActivity.invalidateSearchMenu();
//        Toolbar toolbar = view.findViewById(R.id.toolbar_favourites);
//        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
//        if (appCompatActivity != null) {
//            appCompatActivity.setSupportActionBar(toolbar);
//            toolbar.setTitle("Закладки");
//        }

        AppConstantsKt.APP_ACTIVITY.mToolbar.setTitle("Закладки");

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        MainActivity.drawer.addDrawerListener(toggle);
//        toggle.syncState();

        MainActivity.fab.hide();

        swipeRefreshLayout = view.findViewById(R.id.refresherFavourites);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            itemViewModel.getLiveDataSource().getValue().invalidate();
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.VISIBLE);
        });

        favouritesRecyclerView = view.findViewById(R.id.favouritesRecyclerView);
        getFavourites();
    }

    private void getFavourites () {
        AdAdapter favouritesAdapter = new AdAdapter("FAVOURITES");
        favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);
        itemViewModel.getFavouritesPagedList().observe(getViewLifecycleOwner(), favouritesAdapter::submitList);

        favouritesRecyclerView.setAdapter(favouritesAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isReloaded = true;
    }
}
