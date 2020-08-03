package ragalik.baraxolka.feed.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.feed.data_source.AdDataSource
import ragalik.baraxolka.network.entities.Ad


class AdDataSourceFactory : DataSource.Factory<Int, Ad>() {

    val adLivaDataSource = MutableLiveData<AdDataSource>()

    override fun create(): DataSource<Int, Ad> {
        val adDataSource = AdDataSource()
        adLivaDataSource.postValue(adDataSource)

        return adDataSource
    }
}