package ayaan.mvvmayaan.net.data

import androidx.lifecycle.MutableLiveData
import ayaan.mvvmayaan.net.ServiceApi
import ayaan.mvvmayaan.net.res.SearchRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Copyright (C) 2022 Kakao Corp. All rights reserved.
 *
 * Created on 2022/06/21
 */
class BookRepository
    @Inject
    constructor(private val service: ServiceApi) {
    val result = MutableLiveData<SearchRes?>()

    fun getBookListAsFlow(title :String, page: String): Flow<SearchRes> {
        return flow {
            val search = service.getSearchAsFlow(title, page)
            emit(search)
        }.flowOn(Dispatchers.IO)
    }

    fun getBookList(title :String, page: String): MutableLiveData<SearchRes?> {
        val search = service.getSearch(title, page)

        search.enqueue(object : Callback<SearchRes> {
            override fun onFailure(call: Call<SearchRes>, t: Throwable) {
                result.postValue(null)
            }

            override fun onResponse(call: Call<SearchRes>, response: Response<SearchRes>) {
                result.postValue(response.body())
            }
        })

        return result
    }

    companion object {
        private const val PAGE_SIZE = 25
    }
}
