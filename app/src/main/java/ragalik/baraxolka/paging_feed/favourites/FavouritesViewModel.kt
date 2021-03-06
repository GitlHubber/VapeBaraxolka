package ragalik.baraxolka.paging_feed.favourites

import android.util.Log
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.paging_feed.Ad
import ragalik.baraxolka.paging_feed.AdResponse

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