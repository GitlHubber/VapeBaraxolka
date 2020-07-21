package ragalik.baraxolka.paging_feed.administrator.reports

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.network.entities.Report
import ragalik.baraxolka.network.entities.User


class ReportsDataSourceFactory : DataSource.Factory<Int, Report>() {

    val reportsLivaDataSource = MutableLiveData<ReportsDataSource>()

    override fun create(): DataSource<Int, Report> {
        val reportsDataSource = ReportsDataSource()
        reportsLivaDataSource.postValue(reportsDataSource)

        return reportsDataSource
    }
}