package ragalik.baraxolka.paging_feed.ads

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.paging_feed.Ad


class AdDataSourceFactory : DataSource.Factory<Int, Ad>() {

    val adLivaDataSource = MutableLiveData<AdDataSource>()

    override fun create(): DataSource<Int, Ad> {
        val adDataSource = AdDataSource()
        adLivaDataSource.postValue(adDataSource)

        return adDataSource
    }
}