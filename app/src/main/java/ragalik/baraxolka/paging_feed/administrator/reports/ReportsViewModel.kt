package ragalik.baraxolka.paging_feed.administrator.reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.network.entities.Report

class ReportsViewModel : ViewModel() {

    val reportsPagedList : LiveData<PagedList<Report>>

    val liveDataSource : LiveData<ReportsDataSource>

    init {
        val itemDataSourceFactory = ReportsDataSourceFactory()

        liveDataSource = itemDataSourceFactory.reportsLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(ReportsDataSource.PAGE_SIZE)
                .build()

        reportsPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }
}