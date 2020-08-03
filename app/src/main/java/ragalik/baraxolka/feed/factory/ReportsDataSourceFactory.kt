package ragalik.baraxolka.feed.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.feed.data_source.ReportsDataSource
import ragalik.baraxolka.network.entities.Report


class ReportsDataSourceFactory : DataSource.Factory<Int, Report>() {

    val reportsLivaDataSource = MutableLiveData<ReportsDataSource>()

    override fun create(): DataSource<Int, Report> {
        val reportsDataSource = ReportsDataSource()
        reportsLivaDataSource.postValue(reportsDataSource)

        return reportsDataSource
    }
}