package ragalik.baraxolka.feed.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ragalik.baraxolka.feed.viewmodel.MyAdsViewModel

class ModelFactory (val status: Int, val sellerId: Int, val isSeller: Boolean = false) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAdsViewModel::class.java)) {
            return MyAdsViewModel(status, sellerId, isSeller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}