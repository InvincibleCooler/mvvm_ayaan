package ayaan.mvvmayaan.net

import ayaan.mvvmayaan.net.res.SearchRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ServiceApi {
    // eg) https://api.itbook.store/1.0/search/kotlin/1
    @GET("search/{query}/{page}")
    fun getSearch(@Path("query") query: String, @Path("page") page: String): Call<SearchRes>

    @GET("search/{query}/{page}")
    fun getSearchAsFlow(@Path("query") query: String, @Path("page") page: String): SearchRes
}