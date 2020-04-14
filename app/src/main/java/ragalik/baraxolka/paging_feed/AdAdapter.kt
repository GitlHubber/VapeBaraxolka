package ragalik.baraxolka.paging_feed

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_ad.view.*
import ragalik.baraxolka.R
import ragalik.baraxolka.other_logic.full_ad.FullAdActivity
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.ServerResponse
import ragalik.baraxolka.paging_feed.favourites.SetDeleteBookmark
import ragalik.baraxolka.paging_feed.moderator.AdModerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AdAdapter(private val flag: String = "") : PagedListAdapter <Ad, AdAdapter.AdViewHolder>(AD_COMPARATOR) {

    companion object {
        var activity = MainActivity()
        private val AD_COMPARATOR = object : DiffUtil.ItemCallback<Ad>() {
            override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean =
                oldItem.dateTime == newItem.dateTime

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean =
                newItem == oldItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ad, parent, false)

        return AdViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        val ad = getItem(position)
        val acceptButton = holder.itemView.moderator_accept_button
        val rejectButton = holder.itemView.moderator_reject_button
        val id = ad?.id
        holder.itemView.setOnClickListener {
            val myIntent = Intent(MainActivity.activity, FullAdActivity::class.java)
            myIntent.putExtra("adId", ad?.id)
            MainActivity.activity.startActivity(myIntent)
        }
        holder.itemView.bookmark_button_ads.setOnClickListener {
            if (holder.itemView.bookmark_button_ads.isChecked) {
                SetDeleteBookmark.getInstance().setDeleteBookmark(id!!, it.bookmark_button_ads, "set", MainActivity.activity, ad)
            } else {
                SetDeleteBookmark.getInstance().setDeleteBookmark(id!!, it.bookmark_button_ads, "delete", MainActivity.activity, ad)
            }
        }

        holder.itemView.bookmark_button_ads.isVisible = MainActivity.sp.getInt("id", 0) != 0
        if (flag == "MODERATOR") {
            acceptButton.visibility = View.VISIBLE
            rejectButton.visibility = View.VISIBLE
        }
        acceptButton.setOnClickListener {
            acceptRejectAd(id!!, true)
        }
        rejectButton.setOnClickListener {
            acceptRejectAd(id!!, false)
        }
        ad?.let { holder.bind(ad) }
    }

    private fun acceptRejectAd (id: Int, accepted: Boolean) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("ru"))
        dateFormat.timeZone = TimeZone.getTimeZone("GMT+3")
        val datetime = dateFormat.format(Date())
        val call = ApiClient.getApi().acceptRejectAd(id, if(accepted) 1 else 2, datetime)
        call.enqueue(object : Callback<ServerResponse> {

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.body() != null) {
                    AdModerator.itemViewModel.liveDataSource.value!!.invalidate()
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {

            }
        })
    }

    class AdViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.iv_avatar
        private val title = view.tv_title
        private val location = view.tv_location
        private val datetime = view.ad_preview_date_time
        private val isFavouritesButton = view.bookmark_button_ads
        private val price = view.ad_preview_price

        fun bind (ad: Ad) {
            title.text = ad.title
            location.text = ad.town
            datetime.text = ad.dateTime
            isFavouritesButton.isChecked = ad.isFavourite!!
            price.text = ad.price.toString() + " Руб."

//            Glide.with(imageView.context)
//                    .load(ad.image1url)
//                    .placeholder(R.drawable.jiga_olegovicha)
//                    .into(imageView)

            Picasso.get()
                    .load(ad.image1url)
                    .fit()
                    .centerCrop()
                    .into(imageView)
        }
    }
}
