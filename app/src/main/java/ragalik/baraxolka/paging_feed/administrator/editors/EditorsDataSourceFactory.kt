package ragalik.baraxolka.paging_feed.administrator.editors

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import ragalik.baraxolka.network.entities.User


class EditorsDataSourceFactory : DataSource.Factory<Int, User>() {

    val editorsLivaDataSource = MutableLiveData<EditorsDataSource>()

    override fun create(): DataSource<Int, User> {
        val editorsDataSource = EditorsDataSource()
        editorsLivaDataSource.postValue(editorsDataSource)

        return editorsDataSource
    }
}