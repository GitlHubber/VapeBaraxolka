package ragalik.baraxolka.feed.data_source

import androidx.core.view.isVisible
import androidx.paging.PageKeyedDataSource
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.Ad
import ragalik.baraxolka.network.entities.AdResponse
import ragalik.baraxolka.view.ui.fragment.AdModeratorFragment

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdModeratorDataSource : PageKeyedDataSource<Int, Ad>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Ad>) {
        val call = ApiClient.getApi().getAds(FIRST_PAGE, 3, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<AdResponse>{
            override fun onFailure(call: Call<AdResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<AdResponse>, response: Response<AdResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.ads?.toMutableList()

                    if (responseItems?.isEmpty()!!) {
                        responseItems.add(Ad(-1))
                    }

                    AdModeratorFragment.progressBar.isVisible = false

                    responseItems.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 4)
                    }
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Ad>) {
        val call = ApiClient.getApi().getAds(params.key, 3, MainActivity.sp.getInt("id", 0))
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Ad>) {
        val call = ApiClient.getApi().getAds(params.key, 3, MainActivity.sp.getInt("id", 0))
        call.enqueue(object : Callback<AdResponse> {
            override fun onFailure(call: Call<AdResponse>, t: Throwable) {

            }

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
        })
    }

    companion object {
        const val PAGE_SIZE = 4
        const val FIRST_PAGE = 0
    }
}