package ragalik.baraxolka.paging_feed.moderator

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.paging_feed.Ad


class AdModeratorDataSourceFactory : DataSource.Factory<Int, Ad>() {

    val adModeratorLivaDataSource = MutableLiveData<AdModeratorDataSource>()

    override fun create(): DataSource<Int, Ad> {
        val adModeratorDataSource = AdModeratorDataSource()
        adModeratorLivaDataSource.postValue(adModeratorDataSource)

        return adModeratorDataSource
    }
}