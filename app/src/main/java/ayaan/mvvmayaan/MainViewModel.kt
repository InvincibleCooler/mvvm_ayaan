package ayaan.mvvmayaan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import ayaan.mvvmayaan.net.data.BookRepository
import ayaan.mvvmayaan.net.res.SearchRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(private val repository: BookRepository): ViewModel() {

    val searchRes: MutableLiveData<SearchRes?> = repository.result

    fun getBookList(title: String, page: String = "1") {
        repository.getBookList(title, page)
    }

    fun getBookListAsFlow(title: String, page: String = "1"): LiveData<SearchRes?> {
        return repository.getBookListAsFlow(title, page).asLiveData()
    }
}