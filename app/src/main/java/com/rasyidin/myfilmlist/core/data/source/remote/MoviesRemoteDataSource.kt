package com.rasyidin.myfilmlist.core.data.source.remote

import androidx.paging.DataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.network.MoviesApiService
import com.rasyidin.myfilmlist.core.data.source.remote.paging.MovieDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.CastResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.movies.MovieItemsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class MoviesRemoteDataSource(
    private val apiService: MoviesApiService,
    private val coroutineScope: CoroutineScope
) : ResponseHandle<MovieItemsResponse>() {

    fun getAllNowPlaying(): DataSource.Factory<Int, MovieItemsResponse> =
        object : DataSource.Factory<Int, MovieItemsResponse>() {
            override fun create(): DataSource<Int, MovieItemsResponse> {
                return object : MovieDataSource(coroutineScope) {
                    override val fetch: suspend (Int) -> BaseResponse<MovieItemsResponse>
                        get() = { page ->
                            apiService.getNowPlaying(page = page)
                        }
                }
            }
        }

    fun getAllPopular(): DataSource.Factory<Int, MovieItemsResponse> =
        object : DataSource.Factory<Int, MovieItemsResponse>() {
            override fun create(): DataSource<Int, MovieItemsResponse> {
                return object : MovieDataSource(coroutineScope) {
                    override val fetch: suspend (Int) -> BaseResponse<MovieItemsResponse>
                        get() = { page ->
                            apiService.getPopular(page = page)
                        }
                }
            }
        }

    fun getAllTopRated(): DataSource.Factory<Int, MovieItemsResponse> =
        object : DataSource.Factory<Int, MovieItemsResponse>() {
            override fun create(): DataSource<Int, MovieItemsResponse> {
                return object : MovieDataSource(coroutineScope) {
                    override val fetch: suspend (Int) -> BaseResponse<MovieItemsResponse>
                        get() = { page ->
                            apiService.getTopRated(page = page)
                        }
                }
            }
        }

    fun getAllUpComing(): DataSource.Factory<Int, MovieItemsResponse> =
        object : DataSource.Factory<Int, MovieItemsResponse>() {
            override fun create(): DataSource<Int, MovieItemsResponse> {
                return object : MovieDataSource(coroutineScope) {
                    override val fetch: suspend (Int) -> BaseResponse<MovieItemsResponse>
                        get() = { page ->
                            apiService.getUpcoming(page = page)
                        }
                }
            }
        }

    suspend fun getNowPlaying(): Flow<ApiResponse<List<MovieItemsResponse>>> {
        response = apiService.getNowPlaying()
        return responseHandle(response)
    }

    suspend fun getPopular(): Flow<ApiResponse<List<MovieItemsResponse>>> {
        response = apiService.getPopular()
        return responseHandle(response)
    }

    suspend fun getTopRated(): Flow<ApiResponse<List<MovieItemsResponse>>> {
        response = apiService.getTopRated()
        return responseHandle(response)
    }

    suspend fun getUpcoming(): Flow<ApiResponse<List<MovieItemsResponse>>> {
        response = apiService.getUpcoming()
        return responseHandle(response)
    }

    suspend fun getDetail(movieId: Int): Flow<ApiResponse<MovieItemsResponse>> {
        val response = apiService.getDetailMovie(movieId)
        isIdEqualsToZero = response.id == 0
        return detailResponseHandle(response)
    }

    suspend fun searchMovie(query: String?): Flow<ApiResponse<List<MovieItemsResponse>>> {
        response = apiService.searchMovies(query)
        return responseHandle(response)
    }

    suspend fun getCreditsMovie(movieId: Int): Flow<ApiResponse<List<CastResponse>>> {
        creditsResponse = apiService.getCreditsMovie(movieId)
        return creditsResponseHandle(creditsResponse)
    }
}