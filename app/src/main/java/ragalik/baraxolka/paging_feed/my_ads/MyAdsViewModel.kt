package ragalik.baraxolka.paging_feed.my_ads

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.network.entities.Ad

class MyAdsViewModel(private val adStatus : Int = 0, private val sellerId : Int = 0, private val isSeller : Boolean = false) : ViewModel() {

    val myAdsPagedList : LiveData<PagedList<Ad>>

    val liveDataSource : LiveData<MyAdsDataSource>

    init {
        val itemDataSourceFactory = MyAdsDataSourceFactory(adStatus, sellerId, isSeller)

        liveDataSource = itemDataSourceFactory.myAdsLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MyAdsDataSource.PAGE_SIZE)
                .build()

        myAdsPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()

    }
}
