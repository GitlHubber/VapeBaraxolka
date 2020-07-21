package ragalik.baraxolka.paging_feed.my_reports

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import ragalik.baraxolka.network.entities.Report
import ragalik.baraxolka.paging_feed.administrator.editors.EditorsDataSource

class MyReportsViewModel : ViewModel() {

    val myReportsPagedList : LiveData<PagedList<Report>>

    val liveDataSource : LiveData<MyReportsDataSource>

    init {
        val itemDataSourceFactory = MyReportsDataSourceFactory()

        liveDataSource = itemDataSourceFactory.myReportsLivaDataSource

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(EditorsDataSource.PAGE_SIZE)
                .build()

        myReportsPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
                .build()
    }
}