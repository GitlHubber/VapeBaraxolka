package ragalik.baraxolka.paging_feed.favourites

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.paging_feed.Ad

class FavouritesDataSourceFactory : DataSource.Factory<Int, Ad>() {

    val favouritesLivaDataSource = MutableLiveData<FavouritesDataSource>()

    override fun create(): DataSource<Int, Ad> {
        val favouritesDataSource = FavouritesDataSource()
        favouritesLivaDataSource.postValue(favouritesDataSource)

        return favouritesDataSource
    }
}