package ragalik.baraxolka.paging_feed.administrator

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.network.entities.User
import ragalik.baraxolka.paging_feed.Ad

class AdminViewModel : ViewModel() {

    val adminPagedList : LiveData<PagedList<User>>

    val liveDataSource : LiveData<AdminDataSource>

    init {
        val itemDataSourceFactory = AdminDataSourceFactory()

        liveDataSource = itemDataSourceFactory.adminLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(AdminDataSource.PAGE_SIZE)
                .build()

        adminPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }
}