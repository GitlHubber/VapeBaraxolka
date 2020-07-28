package ragalik.baraxolka.other_logic.account;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ragalik.baraxolka.other_logic.full_ad.FullAdActivity;
import ragalik.baraxolka.other_logic.full_ad.FullAdViewPagerAdapter;
import ragalik.baraxolka.R;


public class FullImageLayout extends Fragment {

    private ViewPager viewPager;
    private FullAdViewPagerAdapter fullAdViewPagerAdapter;
    private int position;
    private ArrayList<String> urls;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_image_layout, container, false);

        if (getArguments() != null) {
            this.position = FullImageLayoutArgs.fromBundle(getArguments()).getPosition();
            this.urls = FullImageLayoutArgs.fromBundle(getArguments()).getUrls();
        }

        fullAdViewPagerAdapter = new FullAdViewPagerAdapter(getActivity(), urls, "Image");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.fullImagePager);
        viewPager.setAdapter(fullAdViewPagerAdapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (FullAdActivity.viewPagerFullAd != null) {
                    FullAdActivity.viewPagerFullAd.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
