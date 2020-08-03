package ragalik.baraxolka.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.feed.data_source.FavouritesDataSource
import ragalik.baraxolka.feed.factory.FavouritesDataSourceFactory
import ragalik.baraxolka.network.entities.Ad

class FavouritesViewModel : ViewModel() {

    val favouritesPagedList : LiveData<PagedList<Ad>>

    val liveDataSource : LiveData<FavouritesDataSource>

    init {
        val itemDataSourceFactory = FavouritesDataSourceFactory()

        liveDataSource = itemDataSourceFactory.favouritesLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(FavouritesDataSource.PAGE_SIZE)
                .build()

        favouritesPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }
}