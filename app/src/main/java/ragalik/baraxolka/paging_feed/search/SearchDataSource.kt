package ragalik.baraxolka.paging_feed.search

import androidx.core.view.isVisible
import androidx.paging.PageKeyedDataSource
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.paging_feed.Ad
import ragalik.baraxolka.paging_feed.AdResponse
import ragalik.baraxolka.paging_feed.ads.ADS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchDataSource (private val where: String, private val sort_field: String, private val sort_type: String) : PageKeyedDataSource<Int, Ad>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Ad>) {
        val call = ApiClient.getApi().getSearchAds(FIRST_PAGE, 1, where, sort_field, sort_type, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<AdResponse> {
            override fun onResponse(call: Call<AdResponse>, response: Response<AdResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.ads
                    ADS.progressBar.isVisible = false

                    responseItems?.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 4)
                    }
                }
            }

            override fun onFailure(call: Call<AdResponse>, t: Throwable) {}
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Ad>) {
        val call = ApiClient.getApi().getSearchAds(params.key, 1, where, sort_field, sort_type, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<AdResponse> {
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

            override fun onFailure(call: Call<AdResponse>, t: Throwable) {}
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Ad>) {
        val call = ApiClient.getApi().getSearchAds(params.key, 1, where, sort_field, sort_type, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<AdResponse> {
            override fun onResponse(call: Call<AdResponse>, response: Response<AdResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.ads

                    val key = if (params.key > 1) params.key - 4 else 0

                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }

            override fun onFailure(call: Call<AdResponse>, t: Throwable) {}
        })
    }

    companion object {
        const val PAGE_SIZE = 4
        const val FIRST_PAGE = 0
    }
}