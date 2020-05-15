package ragalik.baraxolka.paging_feed

import android.annotation.SuppressLint
import android.content.Intent
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.text.bold
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
import ragalik.baraxolka.network.entities.ReasonsResponse
import ragalik.baraxolka.network.entities.ServerResponse
import ragalik.baraxolka.paging_feed.favourites.SetDeleteBookmark
import ragalik.baraxolka.paging_feed.moderator.AdModerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdAdapter(private val flag: String = "") : PagedListAdapter <Ad, AdAdapter.AdViewHolder>(AD_COMPARATOR){

    companion object {
        var activity = MainActivity()
        var reasonPosition = 1
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
        val view = holder.itemView
        val id = ad?.id

        view.setOnClickListener {
            val myIntent = Intent(MainActivity.activity, FullAdActivity::class.java)
            myIntent.putExtra("adId", ad?.id)
            MainActivity.activity.startActivity(myIntent)
        }

        view.bookmark_button_ads.setOnClickListener {
            if (view.bookmark_button_ads.isChecked) {
                SetDeleteBookmark.getInstance().setDeleteBookmark(id!!, it.bookmark_button_ads, "set", MainActivity.activity, ad)
            } else {
                SetDeleteBookmark.getInstance().setDeleteBookmark(id!!, it.bookmark_button_ads, "delete", MainActivity.activity, ad)
            }
        }

        view.bookmark_button_ads.isVisible = MainActivity.sp.getInt("id", 0) != 0

        if (flag == "MODERATOR") {
            var rejectFlag = true

            view.moderator_layout.visibility = View.VISIBLE

            view.moderator_accept_button.setOnClickListener {
                acceptRejectAd(id!!, true)
            }

            view.moderator_reject_button.setOnClickListener {
                if (rejectFlag) {
                    view.rejectReasonLayout.visibility = View.VISIBLE
                    getReasons(view)
                } else {
                    view.rejectReasonLayout.visibility = View.GONE
                }
                rejectFlag = !rejectFlag
            }

            view.acceptRejectButton.setOnClickListener {
                if (reasonPosition == 1) {
                    Toast.makeText(it.context, "Укажите причину", Toast.LENGTH_LONG).show()
                } else {
                    val rejectMessage = view.rejectMessageEditText.editText?.text.toString()
                    acceptRejectAd(id!!, false, rejectMessage)
                    view.rejectReasonLayout.visibility = View.GONE
                    view.rejectMessageEditText.editText?.setText("")
                    view.rejectReasonDropdown.editText?.setText("")
                }
            }
        } else if (flag == "REJECTED") {
            view.rejected_ads_layout.visibility = View.VISIBLE

            val customRejectReason = SpannableStringBuilder()
                    .bold { append("Причина отклонения: ") }
                    .append("${ad?.reasonName}")
            view.tw_reject_reason.text = customRejectReason

            if (ad?.rejectMessage.isNullOrEmpty()) {
                view.tw_reject_message.visibility = View.GONE
            } else {
                val customRejectMes = SpannableStringBuilder()
                        .bold { append("Комментарий к отклонению: ") }
                        .append("${ad?.rejectMessage}")
                view.tw_reject_message.text = customRejectMes
            }
        }

        ad?.let { holder.bind(ad) }
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
            when (ad.region) {
                "Минск" -> {
                    location.text = "${ad.town} р-н"
                }
                "0" -> {
                    location.text = "Регион не указан"
                }
                else -> {
                    location.text = ad.town
                }
            }

            datetime.text = ad.dateTime
            isFavouritesButton.isChecked = ad.isFavourite!!
            price.text = "${ad.price.toString()} Руб."

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

    private fun acceptRejectAd (id: Int, accepted: Boolean, rejectMessage: String = "") {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("ru"))
        dateFormat.timeZone = TimeZone.getTimeZone("GMT+3")
        val datetime = dateFormat.format(Date())
        val call = ApiClient.getApi().acceptRejectAd(id, if(accepted) 1 else 2, datetime, reasonPosition, rejectMessage)
        call.enqueue(object : Callback<ServerResponse> {

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.body() != null) {
                    AdModerator.itemViewModel.liveDataSource.value!!.invalidate()
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {}
        })
    }

    private fun getReasons (view: View) {
        val call = ApiClient.getApi().rejectReasons
        call.enqueue(object : Callback<ReasonsResponse> {

            override fun onResponse(call: Call<ReasonsResponse>, response: Response<ReasonsResponse>) {
                if (response.body() != null) {
                    val reasons = response.body()?.reasons!!
                    val reasonsArr : ArrayList<String> = ArrayList()
                    for (i in reasons) {
                        i.reason_name?.let { reasonsArr.add(it) }
                    }

                    view.rejectReasonDropdownText.setAdapter(ArrayAdapter(MainActivity.activity, R.layout.dropdown_text_color, reasonsArr))
                    view.rejectReasonDropdownText.setOnClickListener { view.rejectReasonDropdownText.showDropDown() }
                    view.rejectReasonDropdownText.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ -> reasonPosition = position + 2 }
                }
            }

            override fun onFailure(call: Call<ReasonsResponse>, t: Throwable) {}
        })
    }
}
