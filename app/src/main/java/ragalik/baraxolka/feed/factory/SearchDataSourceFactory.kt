package ragalik.baraxolka.feed.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.feed.data_source.SearchDataSource
import ragalik.baraxolka.network.entities.Ad

class SearchDataSourceFactory (private val where: String, private val sort_field: String, private val sort_type: String) : DataSource.Factory<Int, Ad>() {

    val searchLivaDataSource = MutableLiveData<SearchDataSource>()

    override fun create(): DataSource<Int, Ad> {
        val searchDataSource = SearchDataSource(where, sort_field, sort_type)
        searchLivaDataSource.postValue(searchDataSource)

        return searchDataSource
    }
}