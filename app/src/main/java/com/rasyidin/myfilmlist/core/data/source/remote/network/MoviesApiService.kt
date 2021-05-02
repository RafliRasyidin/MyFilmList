package com.rasyidin.myfilmlist.core.data.source.remote.network

import com.rasyidin.myfilmlist.BuildConfig.API_KEY
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.CreditsResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.movies.MovieItemsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<MovieItemsResponse>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<MovieItemsResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<MovieItemsResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<MovieItemsResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieItemsResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") querySearch: String?,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): BaseResponse<MovieItemsResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): CreditsResponse

}