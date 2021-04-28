package com.rasyidin.myfilmlist.core.data.source.remote

import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.network.MoviesApiService
import com.rasyidin.myfilmlist.core.data.source.remote.response.movies.MovieItemsResponse
import kotlinx.coroutines.flow.Flow

class MoviesRemoteDataSource(private val apiService: MoviesApiService): ResponseHandle<MovieItemsResponse>() {

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
}