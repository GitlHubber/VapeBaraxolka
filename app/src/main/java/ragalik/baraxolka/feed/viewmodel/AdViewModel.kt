package ragalik.baraxolka.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.feed.data_source.AdDataSource
import ragalik.baraxolka.feed.factory.AdDataSourceFactory
import ragalik.baraxolka.network.entities.Ad

class AdViewModel : ViewModel() {

    val adPagedList : LiveData<PagedList<Ad>>

    val liveDataSource : LiveData<AdDataSource>

    init {
        val itemDataSourceFactory = AdDataSourceFactory()

        liveDataSource = itemDataSourceFactory.adLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(AdDataSource.PAGE_SIZE)
                .build()

        adPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }
}