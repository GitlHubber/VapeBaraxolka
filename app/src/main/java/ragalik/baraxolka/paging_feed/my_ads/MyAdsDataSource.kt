package ragalik.baraxolka.paging_feed.my_ads

import androidx.core.view.isVisible
import androidx.paging.PageKeyedDataSource
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.paging_feed.seller.SellerProfileActivity
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.paging_feed.Ad
import ragalik.baraxolka.paging_feed.AdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAdsDataSource(private val adStatus : Int = 0, private val sellerId : Int = 0, private val isSeller : Boolean = false) : PageKeyedDataSource<Int, Ad>() {


    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Int>, callback: PageKeyedDataSource.LoadInitialCallback<Int, Ad>) {
        val call = ApiClient.getApi().getMyAds(FIRST_PAGE, adStatus, sellerId, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<AdResponse> {
            override fun onFailure(call: Call<AdResponse>, t: Throwable) {
                if (!isSeller) {
                    when (adStatus) {
                        1 -> MyADS.pb_active.isVisible = false
                        2 -> MyADS.pb_rejected.isVisible = false
                        3 -> MyADS.pb_on_moderate.isVisible = false
                        4 -> MyADS.pb_non_active.isVisible = false
                        else -> {}
                    }
                } else {
                    SellerProfileActivity.progressBar.isVisible = false
                }
            }

            override fun onResponse(call: Call<AdResponse>, response: Response<AdResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.ads?.toMutableList()

                    if (responseItems?.isEmpty()!!) {
                        responseItems.add(Ad(-1))
                    }

                    if (!isSeller) {
                        when (adStatus) {
                            1 -> MyADS.pb_active.isVisible = false
                            2 -> MyADS.pb_rejected.isVisible = false
                            3 -> MyADS.pb_on_moderate.isVisible = false
                            4 -> MyADS.pb_non_active.isVisible = false
                            else -> {}
                        }
                    } else {
                        SellerProfileActivity.progressBar.isVisible = false
                    }

                    responseItems.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 4)
                    }
                }
            }
        })
    }

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Ad>) {
        val call = ApiClient.getApi().getMyAds(params.key, adStatus, sellerId, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<AdResponse> {
            override fun onFailure(call: Call<AdResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<AdResponse>, response: Response<AdResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.ads

                    val key = params.key + 4

                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }

        })
    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, Ad>) {
        val call = ApiClient.getApi().getMyAds(params.key, adStatus, sellerId, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<AdResponse> {
            override fun onFailure(call: Call<AdResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<AdResponse>, response: Response<AdResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.ads

                    val key = if (params.key > 4) params.key - 4 else 0

                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }

        })
    }

    companion object {
        const val PAGE_SIZE = 4
        const val FIRST_PAGE = 0
    }
}