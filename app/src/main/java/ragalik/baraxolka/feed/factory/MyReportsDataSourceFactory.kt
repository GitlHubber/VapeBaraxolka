package ragalik.baraxolka.feed.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.feed.data_source.MyReportsDataSource
import ragalik.baraxolka.network.entities.Report


class MyReportsDataSourceFactory : DataSource.Factory<Int, Report>() {

    val myReportsLivaDataSource = MutableLiveData<MyReportsDataSource>()

    override fun create(): DataSource<Int, Report> {
        val myReportsDataSource = MyReportsDataSource()
        myReportsLivaDataSource.postValue(myReportsDataSource)

        return myReportsDataSource
    }
}