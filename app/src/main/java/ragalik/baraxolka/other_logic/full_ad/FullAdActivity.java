package ragalik.baraxolka.other_logic.full_ad;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

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
import ragalik.baraxolka.paging_feed.DateTimeUtils;
import ragalik.baraxolka.paging_feed.favourites.SetDeleteBookmark;
import ragalik.baraxolka.paging_feed.seller.SellerProfileActivity;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.paging_feed.Ad;
import ragalik.baraxolka.paging_feed.FullAdResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullAdActivity extends AppCompatActivity {

    private static int adId;
    private static ArrayList<String> urls;

    public static ViewPager viewPagerFullAd;
    TextView title;
    TextView category;
    TextView subcategory;
    TextView price;
    TextView description;
    TextView views;
    TextView town;
    TextView userNickname;
    CircleImageView userImage;
    TextView adNumber;
    TextView date_time;
    LinearLayout sellerLayout;
    CircleIndicator indicator;
    AppCompatCheckBox bookmark;
    private int user_id;
    //private AdView mAdView;

    public static AppCompatActivity appCompatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_ad_loader);
        appCompatActivity = this;
        urls = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            adId = (Integer)bundle.get("adId");
            getFullAdInfo(adId);
        }
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

    private void getFullAdInfo (int adId) {
        user_id = MainActivity.sp.getInt("id", 0);

        Call<FullAdResponse> call = ApiClient.getApi().getFullAd(adId, user_id);
        call.enqueue(new Callback<FullAdResponse>() {
            @Override
            public void onResponse(Call<FullAdResponse> call, Response<FullAdResponse> response) {
                setContentView(R.layout.activity_full_ad);

                Toolbar toolbar = findViewById(R.id.toolbar_full_ad);
                appCompatActivity.setSupportActionBar(toolbar);
                bookmark = findViewById(R.id.bookmark_button_full_ad);
                if (user_id == 0) {
                    bookmark.setVisibility(View.GONE);
                }

//                mAdView = findViewById(R.id.FullAdAdView);
//                AdRequest adRequest = new AdRequest.Builder().build();
//                mAdView.loadAd(adRequest);


                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
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
                    indicator = (CircleIndicator) findViewById(R.id.circleIndicatorFullAd);

                    viewPagerFullAd = findViewById(R.id.ivFullScreenAd);
                    FullAdViewPagerAdapter fullAdViewPagerAdapter = new FullAdViewPagerAdapter(getApplicationContext(), urls, "AD");
                    viewPagerFullAd.setAdapter(fullAdViewPagerAdapter);
                    indicator.setViewPager(viewPagerFullAd);


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

                    setAdInfo(ad);

                    if (user_id == 0) {
                        bookmark.setVisibility(View.GONE);
                    } else {
                        bookmark.setVisibility(View.VISIBLE);
                        if (ad.isFavourite()) {
                            bookmark.setChecked(true);
                        } else {
                            bookmark.setChecked(false);
                        }
                    }

                    bookmark.setOnClickListener(v -> {
                        if (bookmark.isChecked()) {
                            SetDeleteBookmark.getInstance().setDeleteBookmark(ad.getId(), bookmark, "set", getApplicationContext(), ad);
                        } else {
                            SetDeleteBookmark.getInstance().setDeleteBookmark(ad.getId(), bookmark, "delete", getApplicationContext(), ad);
                        }
                    });

                    sellerLayout = findViewById(R.id.seller_profile_layout);
                    sellerLayout.setOnClickListener(v -> {
                        Intent myIntent = new Intent(FullAdActivity.this, SellerProfileActivity.class);
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
