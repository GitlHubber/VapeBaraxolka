package ragalik.baraxolka.paging_feed.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import ragalik.baraxolka.R;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.User;
import ragalik.baraxolka.paging_feed.AdAdapter;
import ragalik.baraxolka.paging_feed.my_ads.ModelFactory;
import ragalik.baraxolka.paging_feed.my_ads.MyAdsViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerProfileActivity extends AppCompatActivity {

    public static int sellerId;
    public static int seller_ads_status_number;
    private Toolbar toolbar;
    private TextView sellerEmail;
    private TextView sellerPhoneNumber;
    private TextView sellerRegionTown;
    private ImageView sellerProfilePhoto;
    private User sellerProfile;
    public static ProgressBar progressBar;

    private RecyclerView sellerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_ad_loader);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            sellerId = Integer.parseInt(bundle.get("sellerId").toString());
            seller_ads_status_number = 1;
            getSellerInfo(sellerId);
        }
    }

    public void startSellerProfileView() {
        setContentView(R.layout.activity_seller_profile);

        progressBar = findViewById(R.id.progress_seller);
        progressBar.setVisibility(View.VISIBLE);

        toolbar = (Toolbar) findViewById(R.id.toolbarSeller);
        setSupportActionBar(toolbar);
        toolbar.setTitle(sellerProfile.getNickname());

        sellerEmail = findViewById(R.id.sellerEmailProfile);
        sellerPhoneNumber = findViewById(R.id.sellerPhoneNumberProfile);
        sellerRegionTown = findViewById(R.id.sellerRegionTownProfile);
        sellerProfilePhoto = findViewById(R.id.sellerProfilePhoto);

        sellerRecyclerView = findViewById(R.id.sellerAdsProfile);

        sellerEmail.setText(sellerProfile.getEmail());

        if (sellerProfile.getPhoneNumber().equals("0")) {
            sellerPhoneNumber.setText("Номер телефона не указан!");
        } else {
            sellerPhoneNumber.setText(sellerProfile.getPhoneNumber());
        }

        if (sellerProfile.getRegion().equals("0")) {
            sellerRegionTown.setText("Город не указан!");
        } else {
            sellerRegionTown.setText(sellerProfile.getRegion() + " | г. " + sellerProfile.getTown());
        }

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Не стесняйтесь, позвоните!", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (sellerProfile.getImage() != null) {
            String temp = sellerProfile.getImage();
            Picasso.get().invalidate(temp);
            Picasso.get().load(temp).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(sellerProfilePhoto);
        } else {
            sellerProfilePhoto.setImageResource(R.drawable.gradient_navigation);
        }

        getAdsSeller();
    }

    private void getSellerInfo (int sellerId) {
        Call<List<User>> call = ApiClient.getApi().getUserInfo(sellerId);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null) {
                    sellerProfile = response.body().get(0);
                    startSellerProfileView();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAdsSeller () {
        AdAdapter myAdsAdapter = new AdAdapter();
        sellerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdsViewModel itemViewModel = new ViewModelProvider(this, new ModelFactory(1, sellerId, true)).get(MyAdsViewModel.class);
        itemViewModel.getMyAdsPagedList().observe(this, myAdsAdapter::submitList);

        sellerRecyclerView.setAdapter(myAdsAdapter);
    }
}
