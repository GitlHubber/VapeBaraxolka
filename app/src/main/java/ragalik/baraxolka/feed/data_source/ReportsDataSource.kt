package ragalik.baraxolka.feed.data_source

import androidx.core.view.isVisible
import androidx.paging.PageKeyedDataSource
import ragalik.baraxolka.network.ApiClient
import ragalik.baraxolka.network.entities.Report
import ragalik.baraxolka.network.entities.ReportResponse
import ragalik.baraxolka.view.ui.fragment.ReportsFragment

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportsDataSource : PageKeyedDataSource<Int, Report>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Report>) {
        val call = ApiClient.getApi().getReports(FIRST_PAGE)
        call.enqueue(object : Callback<ReportResponse>{
            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<ReportResponse>, response: Response<ReportResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.reports
                    ReportsFragment.reportsProgressBar.isVisible = false

                    responseItems?.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 4)
                    }
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Report>) {
        val call = ApiClient.getApi().getReports(params.key)
        call.enqueue(object : Callback<ReportResponse> {
            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {}

            override fun onResponse(call: Call<ReportResponse>, response: Response<ReportResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.reports

                    val key = params.key + 4

                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Report>) {
        val call = ApiClient.getApi().getReports(params.key)
        call.enqueue(object : Callback<ReportResponse> {
            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<ReportResponse>, response: Response<ReportResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.reports

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