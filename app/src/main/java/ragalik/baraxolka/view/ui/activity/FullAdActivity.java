package ragalik.baraxolka.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.utils.DateTimeUtils;
import ragalik.baraxolka.utils.SetDeleteBookmark;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.Ad;
import ragalik.baraxolka.network.entities.FullAdResponse;
import ragalik.baraxolka.view.adapter.FullAdViewPagerAdapter;
import ragalik.baraxolka.view.ui.fragment.dialog_fragment.SendReportDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullAdActivity extends AppCompatActivity {

    private static int adId;
    private static ArrayList<String> urls;

    public static ViewPager viewPagerFullAd;
    private TextView title;
    private TextView category;
    private TextView subcategory;
    private TextView price;
    private TextView description;
    private TextView views;
    private TextView town;
    private TextView userNickname;
    private CircleImageView userImage;
    private TextView adNumber;
    private TextView date_time;
    private LinearLayout sellerLayout;
    private CircleIndicator indicator;
    private AppCompatCheckBox bookmark;
    private int user_id;
    private Toolbar toolbar;
    private LinearLayout noteLayout;
    private LinearLayout bookmarkLayout;
    private TextView noteText;
    private TextView bookmarkText;

    private ViewStub fullAdViewStub;

    //private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_ad);

//        if (getArguments() != null) {
//            adId = getArguments().getInt("adId");
//            getFullAdInfo(adId);
//        }

        getFullAdInfo(539);

        fullAdViewStub = findViewById(R.id.fullAdViewStub);
        fullAdViewStub.inflate();

        urls = new ArrayList<>();

        AppCompatActivity appCompatActivity = this;
        toolbar = findViewById(R.id.toolbar_full_ad);
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        bookmark = findViewById(R.id.bookmark_button_full_ad);

        //                mAdView = findViewById(R.id.FullAdAdView);
//                AdRequest adRequest = new AdRequest.Builder().build();
//                mAdView.loadAd(adRequest);

        indicator = (CircleIndicator) findViewById(R.id.circleIndicatorFullAd);
        viewPagerFullAd = findViewById(R.id.ivFullScreenAd);

        title = findViewById(R.id.titleTextFullAd);
        //numberPhotoFullAd = findViewById(R.id.numberPhotoFullAd);
        category = findViewById(R.id.categoryTextFullAd);
        subcategory = findViewById(R.id.typeTextFullAd);
        price = findViewById(R.id.priceTextFullAd);
        description = findViewById(R.id.descriptionTextFullAd);
        views = findViewById(R.id.viewsTextFullAd);
        town = findViewById(R.id.townTextFullAd);
        userNickname = findViewById(R.id.sellerName);
        userImage = findViewById(R.id.userImageFullAd);
        adNumber = findViewById(R.id.numberAdTextFullAd);
        date_time = findViewById(R.id.dateTextFullAd);
        sellerLayout = findViewById(R.id.seller_profile_layout);

        noteLayout = findViewById(R.id.fullAdNoteLayout);
        bookmarkLayout = findViewById(R.id.fullAdBookmarkLayout);
        noteText = findViewById(R.id.TW_full_ad_note);
        bookmarkText = findViewById(R.id.TW_full_ad_bookmark);
    }

    private void setAdInfo (Ad ad) {
        title.setText(ad.getTitle());
        category.setText(ad.getCategoryName());
        subcategory.setText(ad.getSubcategoryName());
        String intPrice = ad.getPrice().substring(ad.getPrice().length() - 3);
        if (intPrice.equals(".00")) {
            price.setText(ad.getPrice().substring(0, ad.getPrice().length() - 3) + " Руб.");
        } else {
            price.setText(ad.getPrice() + " Руб.");
        }
        String temp;
        date_time.setText(DateTimeUtils.Companion.getInstance().getNormalizedDatetime(ad.getDateTime()));
        description.setText(ad.getDescription());
        temp = ad.getViews() + "";
        views.setText(temp);

        String regionStr = ad.getRegion();
        String townStr = ad.getTown();

        String endTownStr;
        if (regionStr.equals("Минск")) {
            endTownStr = "г. " + regionStr + " " + townStr + " р-н";
            town.setText(endTownStr);
        } else if (regionStr.equals("0")) {
            town.setText("Регион не указан");
        } else {
            endTownStr = regionStr + " г. " + townStr;
            town.setText(endTownStr);
        }

        userNickname.setText(ad.getUserNickname());

        if (ad.getUserImage() != null) {
            Picasso.get().invalidate(ad.getUserImage());
            Picasso.get().load(ad.getUserImage())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(userImage);
        }

        temp = ad.getId() + "";
        adNumber.setText(temp);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem reportItem = menu.findItem(R.id.action_to_report);
        if (MainActivity.sp.getInt("id", 0) != 0) {
            reportItem.setVisible(true);
        } else {
            reportItem.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.full_ad_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_to_report:
                SendReportDialogFragment dialogFragment = new SendReportDialogFragment(adId);
                dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);
                dialogFragment.show(getSupportFragmentManager(), "SendReportDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getFullAdInfo (int adId) {
        user_id = MainActivity.sp.getInt("id", 0);

        String phoneIdentificator = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<FullAdResponse> call = ApiClient.getApi().getFullAd(adId, user_id, phoneIdentificator);
        call.enqueue(new Callback<FullAdResponse>() {
            @Override
            public void onResponse(Call<FullAdResponse> call, Response<FullAdResponse> response) {

                fullAdViewStub.setVisibility(View.GONE);

                if (user_id == 0) {
                    bookmark.setVisibility(View.GONE);
                }

                if (response.body() != null) {
                    Ad ad = response.body().getAd().get(0);

                    if (!ad.getImage1url().equals("null")) {
                        urls.add(ad.getImage1url());
                    }
                    if (!ad.getImage2url().equals("null")) {
                        urls.add(ad.getImage2url());
                    }
                    if (!ad.getImage3url().equals("null")) {
                        urls.add(ad.getImage3url());
                    }
                    if (!ad.getImage4url().equals("null")) {
                        urls.add(ad.getImage4url());
                    }
                    if (!ad.getImage5url().equals("null")) {
                        urls.add(ad.getImage5url());
                    }

                    FullAdViewPagerAdapter fullAdViewPagerAdapter = new FullAdViewPagerAdapter(getApplicationContext(), urls, "AD");
                    viewPagerFullAd.setAdapter(fullAdViewPagerAdapter);
                    indicator.setViewPager(viewPagerFullAd);

                    setAdInfo(ad);

                    if (user_id == 0) {
                        //bookmark.setVisibility(View.GONE);
                        bookmarkLayout.setVisibility(View.GONE);
                        noteLayout.setVisibility(View.GONE);
                    } else {
                        //bookmark.setVisibility(View.VISIBLE);
                        bookmarkLayout.setVisibility(View.VISIBLE);
                        noteLayout.setVisibility(View.VISIBLE);
                        if (ad.isFavourite()) {
                            bookmark.setChecked(true);
                            bookmarkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                        } else {
                            bookmark.setChecked(false);
                            bookmarkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondaryText));
                        }
                    }

                    bookmarkLayout.setOnClickListener(v -> {
                        if (bookmark.isChecked()) {
                            SetDeleteBookmark.getInstance().setDeleteBookmark(ad.getId(), bookmark, "set", getApplicationContext(), ad);
                            bookmarkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                        } else {
                            SetDeleteBookmark.getInstance().setDeleteBookmark(ad.getId(), bookmark, "delete", getApplicationContext(), ad);
                            bookmarkText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondaryText));
                        }
                    });

                    sellerLayout.setOnClickListener(v -> {
                        Intent myIntent = new Intent(getApplicationContext(), SellerProfileActivity.class);
                        myIntent.putExtra("sellerId", ad.getIdUser());
                        v.getContext().startActivity(myIntent);
                    });
                }
            }

            @Override
            public void onFailure(Call<FullAdResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }
}
