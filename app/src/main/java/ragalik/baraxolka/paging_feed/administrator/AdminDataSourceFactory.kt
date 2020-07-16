package ragalik.baraxolka.paging_feed.administrator

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.network.entities.User
import ragalik.baraxolka.paging_feed.Ad


class AdminDataSourceFactory : DataSource.Factory<Int, User>() {

    val adminLivaDataSource = MutableLiveData<AdminDataSource>()

    override fun create(): DataSource<Int, User> {
        val adminDataSource = AdminDataSource()
        adminLivaDataSource.postValue(adminDataSource)

        return adminDataSource
    }
}