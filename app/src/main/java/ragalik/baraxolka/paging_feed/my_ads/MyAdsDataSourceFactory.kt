package ragalik.baraxolka.paging_feed.my_ads

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.network.entities.Ad

class MyAdsDataSourceFactory(private val adStatus : Int = 0, private val sellerId : Int = 0, private val isSeller : Boolean = false) : DataSource.Factory<Int, Ad>() {

    val myAdsLivaDataSource = MutableLiveData<MyAdsDataSource>()

    override fun create(): DataSource<Int, Ad> {
        val myAdsDataSource = MyAdsDataSource(adStatus, sellerId, isSeller)
        myAdsLivaDataSource.postValue(myAdsDataSource)

        return myAdsDataSource
    }
}