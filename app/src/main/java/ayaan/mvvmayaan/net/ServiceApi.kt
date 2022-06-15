package ayaan.mvvmayaan.net

import ayaan.mvvmayaan.net.res.SearchRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ServiceApi {
    // eg) https://api.itbook.store/1.0/search/kotlin
    @GET("search/{query}")
    fun getSearch(@Path("query") query: String): Call<SearchRes>
}