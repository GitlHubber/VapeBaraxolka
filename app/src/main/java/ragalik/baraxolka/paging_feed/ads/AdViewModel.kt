package ragalik.baraxolka.paging_feed.ads

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
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