package ragalik.baraxolka.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.feed.factory.EditorsDataSourceFactory
import ragalik.baraxolka.feed.data_source.EditorsDataSource
import ragalik.baraxolka.network.entities.User

class EditorsViewModel : ViewModel() {

    val editorsPagedList : LiveData<PagedList<User>>

    val liveDataSource : LiveData<EditorsDataSource>

    init {
        val itemDataSourceFactory = EditorsDataSourceFactory()

        liveDataSource = itemDataSourceFactory.editorsLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(EditorsDataSource.PAGE_SIZE)
                .build()

        editorsPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }
}