package ragalik.baraxolka.paging_feed.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchViewModelFactory (private val where: String, private val sort_field: String, private val sort_type: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(where, sort_field, sort_type) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}