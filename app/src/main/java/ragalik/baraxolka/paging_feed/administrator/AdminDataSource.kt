package ragalik.baraxolka.paging_feed.administrator

import androidx.core.view.isVisible
import androidx.paging.PageKeyedDataSource
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.User
import ragalik.baraxolka.network.entities.UserPreviewResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDataSource : PageKeyedDataSource<Int, User>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        val call = ApiClient.getApi().getUsersPreview(FIRST_PAGE, 2)
        call.enqueue(object : Callback<UserPreviewResponse>{
            override fun onFailure(call: Call<UserPreviewResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<UserPreviewResponse>, response: Response<UserPreviewResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.users
                    EditorsFragment.progressBar.isVisible = false

                    responseItems?.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 4)
                    }
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        val call = ApiClient.getApi().getUsersPreview(params.key, 2)
        call.enqueue(object : Callback<UserPreviewResponse> {
            override fun onFailure(call: Call<UserPreviewResponse>, t: Throwable) {}

            override fun onResponse(call: Call<UserPreviewResponse>, response: Response<UserPreviewResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.users

                    val key = params.key + 4

                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        val call = ApiClient.getApi().getUsersPreview(params.key, 2)
        call.enqueue(object : Callback<UserPreviewResponse> {
            override fun onFailure(call: Call<UserPreviewResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<UserPreviewResponse>, response: Response<UserPreviewResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.users

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