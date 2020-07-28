package ragalik.baraxolka.other_logic.main_fragments;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ragalik.baraxolka.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoInternetConnection extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView retry;

    public NoInternetConnection() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_no_internet_connection, container, false);

        swipeRefreshLayout = v.findViewById(R.id.refresher);
        swipeRefreshLayout.setOnRefreshListener(this);

        retry = v.findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInternetConnection()) {
                    getActivity().setContentView(R.layout.fragment_splash_screen);
                    //SplashScreenFragment.startAnimations(getActivity());
                    //SplashScreenFragment.logoLauncher.start();
                }
            }
        });
        return v;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkInternetConnection()) {
                    getActivity().setContentView(R.layout.fragment_splash_screen);
                   // SplashScreenFragment.startAnimations(getActivity());
                    //SplashScreenFragment.logoLauncher.start();
                }

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 200);
    }

    public boolean checkInternetConnection() {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }

}
