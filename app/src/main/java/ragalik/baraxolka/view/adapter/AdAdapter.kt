package ragalik.baraxolka.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_ad.view.*
import kotlinx.android.synthetic.main.no_ads_view_type.view.*
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.Ad
import ragalik.baraxolka.network.entities.ReasonsResponse
import ragalik.baraxolka.network.entities.ServerResponse
import ragalik.baraxolka.view.ui.activity.AdCreatorActivity
import ragalik.baraxolka.view.ui.activity.EditCreatorActivity
import ragalik.baraxolka.view.ui.fragment.AdsFragment
import ragalik.baraxolka.view.ui.fragment.AdModeratorFragment
import ragalik.baraxolka.view.ui.fragment.MyAdsFragment
import ragalik.baraxolka.utils.DateTimeUtils
import ragalik.baraxolka.view.ui.activity.FullAdActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdAdapter(private val flag: String = "") : PagedListAdapter <Ad, RecyclerView.ViewHolder>(AD_COMPARATOR){

    companion object {
        val AD = 0
        val NO_ADS = 1

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View
                return when (viewType) {
                    AD -> {
                        view = LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false)
                        AdViewHolder(view)
                    }
                    NO_ADS -> {
                        view = LayoutInflater.from(parent.context).inflate(R.layout.no_ads_view_type, parent, false)
                        NoAdsViewHolder(view)
                    }
                    else -> throw Exception()
                }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == AD) {
            val ad = getItem(position)
            val view = holder.itemView
            val id = ad?.id

            view.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("adId", ad?.id!!)
                val myIntent = Intent(MainActivity.activity, FullAdActivity::class.java)
                myIntent.putExtra("adId", ad.id!!)
                MainActivity.activity.startActivity(myIntent)
            }

//            view.bookmark_button_ads.setOnClickListener {
//                if (view.bookmark_button_ads.isChecked) {
//                    SetDeleteBookmark.getInstance().setDeleteBookmark(id!!, it.bookmark_button_ads, "set", MainActivity.activity, ad)
//                } else {
//                    SetDeleteBookmark.getInstance().setDeleteBookmark(id!!, it.bookmark_button_ads, "delete", MainActivity.activity, ad)
//                }
//            }
//
//            view.bookmark_button_ads.isVisible = MainActivity.sp.getInt("id", 0) != 0

            if (flag == "MODERATOR") {
                var rejectFlag = true

                view.moderator_layout.visibility = View.VISIBLE
         //       view.bookmark_button_ads.visibility = View.GONE

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
                        reasonPosition = 1
                    }
                }
            } else if (flag == "REJECTED") {
                view.rejected_ads_layout.visibility = View.VISIBLE
                view.edit_button_ads.visibility = View.VISIBLE
                view.ad_delete_but.visibility = View.VISIBLE
            //    view.bookmark_button_ads.visibility = View.GONE

                view.edit_button_ads.setOnClickListener {
                    val myIntent = Intent(it.context, EditCreatorActivity::class.java)
                    myIntent.putExtra("adId", ad?.id)

                    it.context.startActivity(myIntent)
                }

                view.ad_delete_but.setOnClickListener {
                    deleteDeactivateAd(id, it)
                }

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
            } else if (flag == "ACTIVE") {
                view.edit_button_ads.visibility = View.VISIBLE
                view.ad_deactivate_but.visibility = View.VISIBLE

                view.edit_button_ads.setOnClickListener{
                    val myIntent = Intent(it.context, EditCreatorActivity::class.java)
                    myIntent.putExtra("adId", ad?.id)

                    it.context.startActivity(myIntent)
                }

                view.ad_deactivate_but.setOnClickListener {
                    deleteDeactivateAd(id, it)
                }
            } else if (flag == "NON_ACTIVE") {
                view.ad_delete_but.visibility = View.VISIBLE
          //      view.bookmark_button_ads.visibility = View.GONE

                view.ad_delete_but.setOnClickListener {
                    deleteDeactivateAd(id, it)
                }
            }

            ad?.let { (holder as AdViewHolder).bind(ad) }
        } else {
            (holder as NoAdsViewHolder).bind(flag)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)?.id == -1) {
            NO_ADS
        } else {
            AD
        }
    }

    class NoAdsViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        private val image = view.no_ads_image
        private val message = view.no_ads_message
        private val secondaryMessage = view.no_ads_secondary_message
        private val noAdsButton = view.no_ads_button

        fun bind (flag : String) {
            when (flag) {
                "FAVOURITES" -> {
                    image.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                    message.text = "У вас еще нет закладок"
                    secondaryMessage.text = "Вы можете добавлять их переходя в полное объявление"
                    noAdsButton.visibility = View.VISIBLE
                    noAdsButton.text = "Перейти к объявлениям"
                    noAdsButton.setOnClickListener {
                        val activity = it.context as AppCompatActivity
                        activity.supportFragmentManager.beginTransaction().replace(R.id.constrLayout, AdsFragment()).addToBackStack(null).commit()
                    }
                }
                "ACTIVE" -> {
                    message.text = "У вас нет активных объявлений"
                    secondaryMessage.text = "Вы можете создать объявление прямо сейчас!"
                    noAdsButton.visibility = View.VISIBLE
                    noAdsButton.text = "Создать объявление"
                    noAdsButton.setOnClickListener {
                        val intent = Intent(MainActivity.activity, AdCreatorActivity::class.java)
                        MainActivity.activity.startActivity(intent)
                    }
                }
                "REJECTED" -> {
                    image.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24)
                    message.text = "У вас нет отклоненных объявлений"
                    secondaryMessage.text = "Здесь находятся объявления, которые отклонил модератор"
                    noAdsButton.visibility = View.VISIBLE
                    noAdsButton.text = "Создать объявление"
                    noAdsButton.setOnClickListener {
                        val intent = Intent(MainActivity.activity, AdCreatorActivity::class.java)
                        MainActivity.activity.startActivity(intent)
                    }
                }
                "ON_MODERATE" -> {
                    image.setImageResource(R.drawable.ic_baseline_redo_24)
                    message.text = "У вас нет объявлений на модерации"
                    secondaryMessage.text = "После создания объявления оно попадает на модерацию"
                }
                "NON_ACTIVE" -> {
                    image.setImageResource(R.drawable.ic_baseline_calendar_today_24)
                    message.text = "У вас нет неактивных объявлений"
                    secondaryMessage.text = "Спустя 30 дней после одобрения, объявление станет неактивным и через 30 дней удалится"
                }
                "MODERATOR" -> {
                    image.setImageResource(R.drawable.ic_baseline_emoji_food_beverage_24)
                    message.text = "Нет объявлений на модерации"
                    secondaryMessage.text = "Можете выпить чашечку чая"
                }
            }
        }
    }

    class AdViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.iv_avatar
        private val title = view.tv_title
        private val location = view.tv_location
        private val datetime = view.ad_preview_date_time
        //private val isFavouritesButton = view.bookmark_button_ads
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

            datetime.text = DateTimeUtils.getInstance()?.getNormalizedDatetime(ad.dateTime.toString())
      //      isFavouritesButton.isChecked = ad.isFavourite!!
            val intPrice = ad.price?.substring(ad.price!!.length - 3)
            if (intPrice == ".00") {
                price.text = "${ad.price?.substring(0, ad.price!!.length - 3)} Руб."
            } else {
                price.text = "${ad.price} Руб."
            }

            Picasso.get().invalidate(ad.image1url)
            Picasso.get()
                    .load(ad.image1url)
                    .fit()
                    .centerCrop()
                    .into(imageView)
        }
    }

    private fun deleteDeactivateAd(ad_id: Int?, view: View) {
        val call = ApiClient.getApi().deleteDeactivateAds(ad_id!!, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.isSuccessful) {
                    when (flag) {
                        "ACTIVE" -> {
                            MyAdsFragment.getAdCount(1, MainActivity.sp.getInt("id", 0))
                            MyAdsFragment.activeViewModel.liveDataSource.value?.invalidate()
                            Toast.makeText(view.context, "Объявление деактивировано", Toast.LENGTH_LONG).show()
                        }
                        "NON_ACTIVE" -> {
                            MyAdsFragment.getAdCount(4, MainActivity.sp.getInt("id", 0))
                            MyAdsFragment.nonActiveViewModel.liveDataSource.value?.invalidate()
                            Toast.makeText(view.context, "Объявление удалено", Toast.LENGTH_LONG).show()
                        }
                        "REJECTED" -> {
                            MyAdsFragment.getAdCount(2, MainActivity.sp.getInt("id", 0))
                            MyAdsFragment.rejectedViewModel.liveDataSource.value?.invalidate()
                            Toast.makeText(view.context, "Объявление удалено", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {

            }
        })
    }

    private fun acceptRejectAd (id: Int, accepted: Boolean, rejectMessage: String = "") {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("ru"))
        dateFormat.timeZone = TimeZone.getTimeZone("GMT+3")
        val datetime = dateFormat.format(Date())
        val call = ApiClient.getApi().acceptRejectAd(id, if(accepted) 1 else 2, datetime, reasonPosition, rejectMessage)
        call.enqueue(object : Callback<ServerResponse> {

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                if (response.body() != null) {
                    AdModeratorFragment.itemViewModel.liveDataSource.value!!.invalidate()
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
