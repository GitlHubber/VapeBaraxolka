package ragalik.baraxolka.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.feed.data_source.ReportsDataSource
import ragalik.baraxolka.feed.factory.ReportsDataSourceFactory
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