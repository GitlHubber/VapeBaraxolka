package ragalik.baraxolka.paging_feed.my_ads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModelFactory (val status: Int, val sellerId: Int, val isSeller: Boolean = false) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAdsViewModel::class.java)) {
            return MyAdsViewModel(status, sellerId, isSeller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}