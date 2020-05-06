package ragalik.baraxolka.paging_feed.my_ads;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;

import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.IApi;
import ragalik.baraxolka.network.entities.AdsCount;
import ragalik.baraxolka.paging_feed.AdAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyADS extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ViewPager mPager;
    public static TabLayout mTab_layout;
    private Toolbar toolbar;
    private static RecyclerView activeRecyclerView;
    private static RecyclerView onModerateRecyclerView;
    private static RecyclerView rejectedRecyclerView;
    private static RecyclerView nonActiveRecyclerView;
    private static SwipeRefreshLayout refreshLayout;

    public static ProgressBar pb_active;
    public static ProgressBar pb_on_moderate;
    public static ProgressBar pb_rejected;
    public static ProgressBar pb_non_active;

    private static AdAdapter active_adapter;
    private static AdAdapter on_moderate_adapter;
    private static AdAdapter rejected_adapter;
    private static AdAdapter non_active_adapter;

    private static MyAdsViewModel activeViewModel;
    private static MyAdsViewModel onModerateViewModel;
    private static MyAdsViewModel rejectedViewModel;
    private static MyAdsViewModel nonActiveViewModel;

    private static IApi apiClient;

    private static int activeCount;
    private static int onModerateCount;
    private static int rejectedCount;
    private static int nonActiveCount;


    public static String[] TABS= {"0\nАктивные", "0\nНа модерации", "0\nОтклоненные", "0\nНеактивные"};

    public MyADS() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_ads, container, false);
        MainActivity.isActualFragment = false;
        MainActivity.invalidateSearchMenu();

        toolbar = view.findViewById(R.id.toolbar_myAds);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            toolbar.setTitle("Мои объявления");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.drawer.addDrawerListener(toggle);
        toggle.syncState();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.fab.hide();

        mPager = (ViewPager) view.findViewById(R.id.MY_ADS_View_Pager);
        mPager.setOffscreenPageLimit(4);
        mPager.setAdapter(new MyAdsPagerAdapter(getChildFragmentManager()));
        mTab_layout = (TabLayout) view.findViewById(R.id.MY_ADS_Tab_Layout);
        mTab_layout.setupWithViewPager(mPager);

        refreshLayout = view.findViewById(R.id.myAdsRefresher);
        refreshLayout.setOnRefreshListener(this);

        apiClient = ApiClient.getApi();

        activeCount = 0;
        rejectedCount = 0;
        onModerateCount = 0;
        nonActiveCount = 0;
    }


    class MyAdsPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] childFragments;

        public MyAdsPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            childFragments = new Fragment[] {
                    new ActiveAds(),
                    new OnModerateAds(),
                    new RejectedAds(),
                    new NonActiveAds()
            };
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return childFragments[position];
        }

        @Override
        public int getCount() {
            return childFragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }
    }

    @Override
    public void onRefresh() {
        switch (mPager.getCurrentItem()) {
            case 0:
                getAdCount(1, MainActivity.sp.getInt("id", 0));
                activeViewModel.getLiveDataSource().getValue().invalidate();
                refreshLayout.setRefreshing(false);
                pb_active.setVisibility(View.VISIBLE);
                break;
            case 1:
                getAdCount(3, MainActivity.sp.getInt("id", 0));
                onModerateViewModel.getLiveDataSource().getValue().invalidate();
                refreshLayout.setRefreshing(false);
                pb_on_moderate.setVisibility(View.VISIBLE);
            case 2:
                getAdCount(2, MainActivity.sp.getInt("id", 0));
                rejectedViewModel.getLiveDataSource().getValue().invalidate();
                refreshLayout.setRefreshing(false);
                pb_rejected.setVisibility(View.VISIBLE);
            case 3:
                getAdCount(4, MainActivity.sp.getInt("id", 0));
                nonActiveViewModel.getLiveDataSource().getValue().invalidate();
                refreshLayout.setRefreshing(false);
                pb_non_active.setVisibility(View.VISIBLE);
        }
    }

    public static void getAdCount(int status, int id) {

        apiClient.getMyAdsCount(status, id).enqueue(new Callback<AdsCount>() {
            @Override
            public void onResponse(Call<AdsCount> call, Response<AdsCount> response) {
                if (response.body() != null) {
                    switch (status) {
                        case 1:
                            activeCount = response.body().getCount();
                            break;
                        case 2:
                            rejectedCount = response.body().getCount();
                            break;
                        case 3:
                            onModerateCount = response.body().getCount();
                            break;
                        case 4:
                            nonActiveCount = response.body().getCount();
                            break;
                    }
                }

                if (status == 1) {
                    mTab_layout.getTabAt(0).setText(""+ activeCount +"\nАктивные");
                } else if (status == 3) {
                    mTab_layout.getTabAt(1).setText(""+ onModerateCount +"\nНа модерации");
                } else if (status == 2) {
                    mTab_layout.getTabAt(2).setText(""+ rejectedCount +"\nОтклоненные");
                } else if (status == 4) {
                    mTab_layout.getTabAt(3).setText(""+ nonActiveCount +"\nНеактивные");
                }

            }

            @Override
            public void onFailure(Call<AdsCount> call, Throwable t) {

            }
        });

    }

    public static class ActiveAds extends Fragment {
        private View view;
        private boolean isReloaded;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            isReloaded = false;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.active_ads, container, false);

            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            pb_active = view.findViewById(R.id.progress_active);
            if (isReloaded) {
                pb_active.setVisibility(View.GONE);
            } else {
                pb_active.setVisibility(View.VISIBLE);
            }
            activeRecyclerView = view.findViewById(R.id.ActiveAdsView);

            active_adapter = new AdAdapter();
            activeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            activeViewModel = new ViewModelProvider(this, new ModelFactory(1, MainActivity.sp.getInt("id", 0), false)).get(MyAdsViewModel.class);
            activeViewModel.getMyAdsPagedList().observe(getViewLifecycleOwner(), active_adapter::submitList);

            activeRecyclerView.setAdapter(active_adapter);
            getAdCount(1, MainActivity.sp.getInt("id", 0));
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            isReloaded = true;
        }
    }

    public static class OnModerateAds extends Fragment {
        View view;
        private boolean isReloaded;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            isReloaded = false;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.on_moderate_ads, container, false);

            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            pb_on_moderate = view.findViewById(R.id.progress_on_moderate);
            if (isReloaded) {
                pb_on_moderate.setVisibility(View.GONE);
            } else {
                pb_on_moderate.setVisibility(View.VISIBLE);
            }
            onModerateRecyclerView = view.findViewById(R.id.OnModerateAdsView);

            on_moderate_adapter = new AdAdapter();
            onModerateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            onModerateViewModel = new ViewModelProvider(this, new ModelFactory(3, MainActivity.sp.getInt("id", 0), false)).get(MyAdsViewModel.class);
            onModerateViewModel.getMyAdsPagedList().observe(getViewLifecycleOwner(), on_moderate_adapter::submitList);

            onModerateRecyclerView.setAdapter(on_moderate_adapter);
            getAdCount(3, MainActivity.sp.getInt("id", 0));
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            isReloaded = true;
        }
    }

    public static class RejectedAds extends Fragment {
        View view;
        private boolean isReloaded;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            isReloaded = false;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.rejected_ads, container, false);

            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            pb_rejected = view.findViewById(R.id.progress_rejected);
            if (isReloaded) {
                pb_rejected.setVisibility(View.GONE);
            } else {
                pb_rejected.setVisibility(View.VISIBLE);
            }
            rejectedRecyclerView = view.findViewById(R.id.RejectedAdsView);

            rejected_adapter = new AdAdapter("REJECTED");
            rejectedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            rejectedViewModel = new ViewModelProvider(this, new ModelFactory(2, MainActivity.sp.getInt("id", 0), false)).get(MyAdsViewModel.class);
            rejectedViewModel.getMyAdsPagedList().observe(getViewLifecycleOwner(), rejected_adapter::submitList);

            rejectedRecyclerView.setAdapter(rejected_adapter);
            getAdCount(2, MainActivity.sp.getInt("id", 0));
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            isReloaded = true;
        }
    }

    public static class NonActiveAds extends Fragment {
        View view;
        private boolean isReloaded;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            isReloaded = false;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.non_active_ads, container, false);

            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            pb_non_active = view.findViewById(R.id.progress_non_active);
            if (isReloaded) {
                pb_non_active.setVisibility(View.GONE);
            } else {
                pb_non_active.setVisibility(View.VISIBLE);
            }
            nonActiveRecyclerView = view.findViewById(R.id.NonActiveAdsView);

            non_active_adapter = new AdAdapter();
            nonActiveRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            nonActiveViewModel = new ViewModelProvider(this, new ModelFactory(4, MainActivity.sp.getInt("id", 0), false)).get(MyAdsViewModel.class);
            nonActiveViewModel.getMyAdsPagedList().observe(getViewLifecycleOwner(), non_active_adapter::submitList);

            nonActiveRecyclerView.setAdapter(non_active_adapter);
            getAdCount(4, MainActivity.sp.getInt("id", 0));
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            isReloaded = true;
        }
    }
}