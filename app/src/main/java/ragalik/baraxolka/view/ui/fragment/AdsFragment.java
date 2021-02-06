package ragalik.baraxolka.view.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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

import com.google.android.material.chip.Chip;

import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.R;
import ragalik.baraxolka.feed.factory.SearchViewModelFactory;
import ragalik.baraxolka.feed.viewmodel.AdViewModel;
import ragalik.baraxolka.feed.viewmodel.SearchViewModel;
import ragalik.baraxolka.view.adapter.AdAdapter;
import ragalik.baraxolka.view.ui.activity.FilterActivity;
import ragalik.baraxolka.view.ui.activity.SearchActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdsFragment extends Fragment {

    private static SwipeRefreshLayout swipeRefreshLayout;
    public static boolean isFilteredAds;
    private static boolean isChipCanceled;
    public static boolean isFilteredByCategories;
    private SearchViewModel searchViewModel;
    public static AdViewModel adViewModel;
    public static ProgressBar progressBar;
    private Chip searchChip;
    private Chip filterChip;
    private Chip categoryChip;
    Toolbar toolbar;


    private RecyclerView adsRecyclerView;
    private boolean isReloaded;

    public AdsFragment() {
        super(R.layout.fragment_ads);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isReloaded = false;
        //MainActivity.checkUserStatus();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.isActualFragment = true;
        MainActivity.invalidateSearchMenu();
        //AppConstantsKt.APP_ACTIVITY.mToolbar.setTitle("Объявления");

        toolbar = view.findViewById(R.id.ads_toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            toolbar.setTitle("Все объявления");
        }
        //AppConstantsKt.APP_ACTIVITY.mAppDrawer.enableDrawer(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.drawer.addDrawerListener(toggle);
        toggle.syncState();

        progressBar = view.findViewById(R.id.progress_ads);
        if (isReloaded) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }

        searchChip = view.findViewById(R.id.search_chip);
        filterChip = view.findViewById(R.id.filter_chip);
        categoryChip = view.findViewById(R.id.category_chip);

        searchChip.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.activity, SearchActivity.class);
            startActivity(myIntent);
        });

        searchChip.setOnCloseIconClickListener(v -> {
            searchChip.setVisibility(View.GONE);
            isFilteredAds = false;
            isChipCanceled = true;
            getActivity().recreate();
        });

        filterChip.setOnCloseIconClickListener(v -> {
            filterChip.setVisibility(View.GONE);
            isFilteredByCategories = false;
            getActivity().recreate();
        });

        categoryChip.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.activity, FilterActivity.class);
            startActivity(myIntent);
        });

        if (!MainActivity.sp.getString("nickname", "").equals("")) {
            MainActivity.fab.show();
        } else {
            MainActivity.fab.hide();
        }

        setRetainInstance(true);

        swipeRefreshLayout = view.findViewById(R.id.refresherAds);
        adsRecyclerView = view.findViewById(R.id.ADSRecyclerView);
        showAds();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (isFilteredAds || isFilteredByCategories) {
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
            filterChip.setVisibility(View.GONE);

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
        } else if (isFilteredByCategories) {
            searchChip.setVisibility(View.GONE);
            filterChip.setVisibility(View.VISIBLE);

            String where = "";
            if (!FilterActivity.Companion.getCategoryFromSpinner().equals("")) {
                where = "categories.category_name = '" + FilterActivity.Companion.getCategoryFromSpinner() + "'";
                if (!FilterActivity.Companion.getSubcategoryFromSpinner().equals("")) {
                    where += " AND subcategories.subcategory_name = '" + FilterActivity.Companion.getSubcategoryFromSpinner() + "'";
                }
            }

            if (FilterActivity.Companion.getCategoryFromSpinner().equals("") && FilterActivity.Companion.getSubcategoryFromSpinner().equals("")) {
                where = "ad.title LIKE '%%'";
            }

            String sort_type = "DESC";

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
