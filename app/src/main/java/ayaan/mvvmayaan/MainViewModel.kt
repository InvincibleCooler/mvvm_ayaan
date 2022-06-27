package ayaan.mvvmayaan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ayaan.mvvmayaan.net.data.BookRepository
import ayaan.mvvmayaan.net.res.SearchRes


class MainViewModel(repository: BookRepository) : ViewModel() {
    val searchRes: MutableLiveData<SearchRes?> = repository.result

}

class MainViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}