package ragalik.baraxolka.paging_feed.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.network.entities.Ad

class SearchViewModel(where: String, sort_field: String, sort_type: String) : ViewModel() {

    val searchPagedList : LiveData<PagedList<Ad>>

    val liveDataSource : LiveData<SearchDataSource>

    init {
        val itemDataSourceFactory = SearchDataSourceFactory(where, sort_field, sort_type)

        liveDataSource = itemDataSourceFactory.searchLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(SearchDataSource.PAGE_SIZE)
                .build()

        searchPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }
}