package com.rasyidin.myfilmlist.core.data.source.remote.network

import com.rasyidin.myfilmlist.BuildConfig.API_KEY
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApiService {

    @GET("tv/airing_today")
    suspend fun getAiringToday(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<TvItemsResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAir(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<TvItemsResponse>

    @GET("tv/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<TvItemsResponse>

    @GET("tv/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<TvItemsResponse>

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): TvItemsResponse

    @GET("search/tv")
    suspend fun searchTvShow(
        @Query("query") querySearch: String?,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<TvItemsResponse>
}