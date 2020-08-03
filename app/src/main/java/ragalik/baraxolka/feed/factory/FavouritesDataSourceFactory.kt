package ragalik.baraxolka.feed.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.feed.data_source.FavouritesDataSource
import ragalik.baraxolka.network.entities.Ad

class FavouritesDataSourceFactory : DataSource.Factory<Int, Ad>() {

    val favouritesLivaDataSource = MutableLiveData<FavouritesDataSource>()

    override fun create(): DataSource<Int, Ad> {
        val favouritesDataSource = FavouritesDataSource()
        favouritesLivaDataSource.postValue(favouritesDataSource)

        return favouritesDataSource
    }
}