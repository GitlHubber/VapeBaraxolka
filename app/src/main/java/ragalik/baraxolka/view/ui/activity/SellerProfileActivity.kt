package ragalik.baraxolka.view.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import ragalik.baraxolka.R
import ragalik.baraxolka.feed.factory.ModelFactory
import ragalik.baraxolka.feed.viewmodel.MyAdsViewModel
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.User
import ragalik.baraxolka.view.adapter.AdAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerProfileActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var sellerEmail: TextView
    private lateinit var sellerPhoneNumber: TextView
    private lateinit var sellerRegionTown: TextView
    private lateinit var sellerProfilePhoto: ImageView
    private lateinit var sellerProfile: User
    private lateinit var sellerRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_ad_loader)
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            sellerId = bundle["sellerId"].toString().toInt()
            seller_ads_status_number = 1
            getSellerInfo(sellerId)
        }
    }

    fun startSellerProfileView() {
        setContentView(R.layout.activity_seller_profile)
        progressBar = findViewById(R.id.progress_seller)
        progressBar.visibility = View.VISIBLE
        toolbar = findViewById<View>(R.id.toolbarSeller) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = sellerProfile.nickname
        sellerEmail = findViewById(R.id.sellerEmailProfile)
        sellerPhoneNumber = findViewById(R.id.sellerPhoneNumberProfile)
        sellerRegionTown = findViewById(R.id.sellerRegionTownProfile)
        sellerProfilePhoto = findViewById(R.id.sellerProfilePhoto)
        sellerRecyclerView = findViewById(R.id.sellerAdsProfile)
        sellerEmail.text = sellerProfile.email?.substring(0, 1)?.toUpperCase() + sellerProfile.email?.substring(1)
        if (sellerProfile.phoneNumber == sellerProfile.email) {
            sellerPhoneNumber.text = "Номер телефона не указан"
        } else if (sellerProfile.isPhoneHide != null) {
            if (sellerProfile.isPhoneHide == 0) {
                val pn = sellerProfile.phoneNumber
                val resultPN = (pn!!.substring(0, 4) + " (" + pn.substring(4, 6) + ") " + pn.substring(6, 9) + "-"
                        + pn.substring(9, 11) + "-" + pn.substring(11, 13))
                sellerPhoneNumber.text = resultPN
            } else if (sellerProfile.isPhoneHide == 1) {
                sellerPhoneNumber.visibility = View.GONE
            }
        }
        val region = sellerProfile.region
        val town = sellerProfile.town
        val townStr: String
        if (region == "Минск") {
            townStr = "г. $region $town р-н"
            sellerRegionTown.text = townStr
        } else if (region == "0") {
            sellerRegionTown.text = "Регион не указан"
        } else {
            townStr = "$region г. $town"
            sellerRegionTown.text = townStr
        }

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Не стесняйтесь, позвоните!", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (sellerProfile.image != null) {
            val temp = sellerProfile.image
            Picasso.get().invalidate(temp)
            Picasso.get().load(temp).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(sellerProfilePhoto)
        } else {
            sellerProfilePhoto.setImageResource(R.drawable.gradient_navigation)
        }
        getAdsSeller()
    }

    private fun getSellerInfo(sellerId: Int) {
        val call = ApiClient.getApi().getUserInfo(sellerId)
        call.enqueue(object : Callback<List<User?>?> {
            override fun onResponse(call: Call<List<User?>?>, response: Response<List<User?>?>) {
                if (response.body() != null) {
                    sellerProfile = response.body()!![0]!!
                    startSellerProfileView()
                }
            }

            override fun onFailure(call: Call<List<User?>?>, t: Throwable) {
                Toast.makeText(applicationContext, "Произошла ошибка", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getAdsSeller() {
            val myAdsAdapter = AdAdapter()
            sellerRecyclerView.layoutManager = LinearLayoutManager(this)
            val itemViewModel = ViewModelProvider(this, ModelFactory(1, sellerId, true)).get(MyAdsViewModel::class.java)
            itemViewModel.myAdsPagedList.observe(this, { myAdsAdapter.submitList(it) })
            sellerRecyclerView.adapter = myAdsAdapter
        }

    companion object {
        var sellerId = 0
        var seller_ads_status_number = 0
        lateinit var progressBar: ProgressBar
    }
}