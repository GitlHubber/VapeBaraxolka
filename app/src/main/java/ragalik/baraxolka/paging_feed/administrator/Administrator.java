package ragalik.baraxolka.paging_feed.administrator;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;


public class Administrator extends Fragment {

    private BottomNavigationView bottomView;

    public Administrator() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_administrator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomView = view.findViewById(R.id.bottomNavigationView);

        bottomView.setOnNavigationItemSelectedListener(navListener);
        bottomView.setSelectedItemId(R.id.editors_item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.editors_item:
                selectedFragment = new EditorsFragment();
                break;
            case R.id.report_item:
                selectedFragment = new ReportFragment();
                break;
        }

        if (selectedFragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }

        return true;
    };

}
