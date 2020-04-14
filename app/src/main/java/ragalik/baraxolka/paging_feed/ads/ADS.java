package ragalik.baraxolka.paging_feed.ads;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LivePagedListBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;


import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.google.android.material.chip.Chip;

import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.R;
import ragalik.baraxolka.paging_feed.search.SearchActivity;
import ragalik.baraxolka.paging_feed.AdAdapter;
import ragalik.baraxolka.paging_feed.search.SearchViewModel;
import ragalik.baraxolka.paging_feed.search.SearchViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ADS extends Fragment {

    private static SwipeRefreshLayout swipeRefreshLayout;
    public static boolean isFilteredAds;
    private static boolean isChipCanceled;
    private SearchViewModel searchViewModel;
    public static AdViewModel adViewModel;
    public static ProgressBar progressBar;
    private Chip searchChip;

    private RecyclerView adsRecyclerView;
    private boolean isReloaded;
    private static View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isReloaded = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ads, container, false);
        MainActivity.isActualFragment = true;
        MainActivity.invalidateSearchMenu();
        Toolbar toolbar = v.findViewById(R.id.ads_toolbar);
        MainActivity.navigationView.setCheckedItem(R.id.ADS);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            toolbar.setTitle("Объявления");
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.drawer.addDrawerListener(toggle);
        toggle.syncState();
        progressBar = v.findViewById(R.id.progress_ads);
        if (isReloaded) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }

        searchChip = v.findViewById(R.id.search_chip);
        searchChip.setOnCloseIconClickListener(v -> {
            searchChip.setVisibility(View.GONE);
            isFilteredAds = false;
            isChipCanceled = true;
            getActivity().recreate();
        });

        if (!MainActivity.sp.getString("nickname", "").equals("")) {
            MainActivity.fab.show();
        } else {
            MainActivity.fab.hide();
        }

        setRetainInstance(true);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.refresherAds);
        adsRecyclerView = v.findViewById(R.id.ADSRecyclerView);
        showAds();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (isFilteredAds) {
                searchViewModel.getLiveDataSource().getValue().invalidate();
            } else {
                adViewModel.getLiveDataSource().getValue().invalidate();
            }
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.VISIBLE);
        });
    }

    private void showAds () {
        if (isFilteredAds) {
            searchChip.setVisibility(View.VISIBLE);

            String where = "";
            for (String str : SearchActivity.searchRequests.values()) {
                where += str + " AND ";
            }

            if (SearchActivity.searchRequests.size() > 0) {
                where = where.substring(0, where.length() - 5);
            }
            if (SearchActivity.searchRequests.size() == 0) {
                where = "ad.title LIKE '%%'";
            }

            String sort_type;
            if (SearchActivity.sort_type) {
                sort_type = "ASC";
            } else {
                sort_type = "DESC";
            }

            AdAdapter searchAdapter = new AdAdapter();
            adsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            searchViewModel = new ViewModelProvider(this, new SearchViewModelFactory(where, SearchActivity.sort_field, sort_type)).get(SearchViewModel.class);
            searchViewModel.getSearchPagedList().observe(getViewLifecycleOwner(), searchAdapter::submitList);
            adsRecyclerView.setAdapter(searchAdapter);

        } else if (isChipCanceled || !isFilteredAds) {
            searchChip.setVisibility(View.GONE);
            AdAdapter adAdapter = new AdAdapter();
            adsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adViewModel = new ViewModelProvider(this).get(AdViewModel.class);
            adViewModel.getAdPagedList().observe(getViewLifecycleOwner(), adAdapter::submitList);
            adsRecyclerView.setAdapter(adAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isReloaded = true;
    }
}
