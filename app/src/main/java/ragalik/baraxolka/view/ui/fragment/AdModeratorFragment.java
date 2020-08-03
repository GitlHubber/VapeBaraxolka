package ragalik.baraxolka.view.ui.fragment;


import android.content.Context;
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
import ragalik.baraxolka.feed.viewmodel.AdModeratorViewModel;
import ragalik.baraxolka.view.adapter.AdAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdModeratorFragment extends Fragment {
    public static Context context;

    private static final String url_get_ads = MainActivity.SERVER_URL + "getAds.php";
    private static final String url_get_ads_count = MainActivity.SERVER_URL + "getAdCount.php";
    private static final String STATUS_TAG = "3";

    private SwipeRefreshLayout swipeRefreshLayout;
    public static AdModeratorViewModel itemViewModel;
    private RecyclerView adModeratorView;
    public static ProgressBar progressBar;
    private boolean isReloaded;

    public AdModeratorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isReloaded = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);
        MainActivity.isActualFragment = false;
        MainActivity.invalidateSearchMenu();
        return inflater.inflate(R.layout.fragment_ad_moderator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_moderator);
        if (isReloaded) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }

        Toolbar toolbar = view.findViewById(R.id.adModeratorToolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            toolbar.setTitle("Объявления модератора");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.drawer.addDrawerListener(toggle);
        toggle.syncState();

        context = getContext();

        swipeRefreshLayout = view.findViewById(R.id.refresherModerator);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            itemViewModel.getLiveDataSource().getValue().invalidate();
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.VISIBLE);
        });
        adModeratorView = view.findViewById(R.id.ModeratorAdsView);
        //new AdsFeed(STATUS_TAG, url_get_ads, url_get_ads_count, "Редактор", swipeRefreshLayout, adModeratorView, getContext(), getActivity());
        getAds();
        MainActivity.fab.hide();
    }

    private void getAds () {
        AdAdapter adModeratorAdapter = new AdAdapter("MODERATOR");
        adModeratorView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemViewModel = new ViewModelProvider(this).get(AdModeratorViewModel.class);
        itemViewModel.getAdModeratorPagedList().observe(getViewLifecycleOwner(), adModeratorAdapter::submitList);

        adModeratorView.setAdapter(adModeratorAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isReloaded = true;
    }
}
