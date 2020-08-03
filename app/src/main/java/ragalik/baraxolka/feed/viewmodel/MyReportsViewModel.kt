package ragalik.baraxolka.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.network.entities.Report
import ragalik.baraxolka.feed.data_source.EditorsDataSource
import ragalik.baraxolka.feed.data_source.MyReportsDataSource
import ragalik.baraxolka.feed.factory.MyReportsDataSourceFactory

class MyReportsViewModel : ViewModel() {

    val myReportsPagedList : LiveData<PagedList<Report>>

    val liveDataSource : LiveData<MyReportsDataSource>

    init {
        val itemDataSourceFactory = MyReportsDataSourceFactory()

        liveDataSource = itemDataSourceFactory.myReportsLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(EditorsDataSource.PAGE_SIZE)
                .build()

        myReportsPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }
}