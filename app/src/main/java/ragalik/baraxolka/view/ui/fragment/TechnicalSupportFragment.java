package ragalik.baraxolka.view.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TechnicalSupportFragment extends BaseFragment {

    private TextView link;

    public TechnicalSupportFragment() {
        super(R.layout.fragment_technical_support);
    }

    //private AdView mAdView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.fab.hide();
        MainActivity.isActualFragment = false;
        MainActivity.invalidateSearchMenu();

//        Toolbar toolbar = view.findViewById(R.id.supportToolbar);
//        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
//        if (appCompatActivity != null) {
//            appCompatActivity.setSupportActionBar(toolbar);
//            toolbar.setTitle("Техническая поддержка");
//        }

        //AppConstantsKt.APP_ACTIVITY.mToolbar.setTitle("Техническая поддержка");

//        mAdView = getActivity().findViewById(R.id.SupportAdView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        MainActivity.drawer.addDrawerListener(toggle);
//        toggle.syncState();

        link = view.findViewById(R.id.VKGroupLink);
        link.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/v_baraxolka"));
            startActivity(intent);
        });
    }
}
