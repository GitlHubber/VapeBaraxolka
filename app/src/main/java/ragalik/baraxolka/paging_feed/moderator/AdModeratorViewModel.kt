package ragalik.baraxolka.paging_feed.moderator

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.BoundaryCallback
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.paging_feed.Ad

class AdModeratorViewModel : ViewModel() {

    val adModeratorPagedList : LiveData<PagedList<Ad>>

    val liveDataSource : LiveData<AdModeratorDataSource>

    init {
        val itemDataSourceFactory = AdModeratorDataSourceFactory()

        liveDataSource = itemDataSourceFactory.adModeratorLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(AdModeratorDataSource.PAGE_SIZE)
                .build()

        adModeratorPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .setBoundaryCallback(object : BoundaryCallback<Ad>(){
                    override fun onZeroItemsLoaded() {
                        super.onZeroItemsLoaded()
                        Toast.makeText(MainActivity.activity, "PIZDA", Toast.LENGTH_LONG).show()
                    }
                })
                .build()
    }
}