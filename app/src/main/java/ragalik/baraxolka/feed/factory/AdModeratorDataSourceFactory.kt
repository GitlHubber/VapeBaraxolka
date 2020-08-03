package ragalik.baraxolka.feed.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.feed.data_source.AdModeratorDataSource
import ragalik.baraxolka.network.entities.Ad


class AdModeratorDataSourceFactory : DataSource.Factory<Int, Ad>() {

    val adModeratorLivaDataSource = MutableLiveData<AdModeratorDataSource>()

    override fun create(): DataSource<Int, Ad> {
        val adModeratorDataSource = AdModeratorDataSource()
        adModeratorLivaDataSource.postValue(adModeratorDataSource)

        return adModeratorDataSource
    }
}