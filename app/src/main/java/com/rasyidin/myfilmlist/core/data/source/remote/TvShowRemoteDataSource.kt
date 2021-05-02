package com.rasyidin.myfilmlist.core.data.source.remote

import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.network.TvApiService
import com.rasyidin.myfilmlist.core.data.source.remote.response.CastResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse
import kotlinx.coroutines.flow.Flow

class TvShowRemoteDataSource(private val apiService: TvApiService) : ResponseHandle<TvItemsResponse>() {

    suspend fun getAiringToday(): Flow<ApiResponse<List<TvItemsResponse>>> {
        response = apiService.getAiringToday()
        return responseHandle(response)
    }

    suspend fun getOnTheAir(): Flow<ApiResponse<List<TvItemsResponse>>> {
        response = apiService.getOnTheAir()
        return responseHandle(response)
    }

    suspend fun getPopular(): Flow<ApiResponse<List<TvItemsResponse>>> {
        response = apiService.getPopular()
        return responseHandle(response)
    }

    suspend fun getTopRated(): Flow<ApiResponse<List<TvItemsResponse>>> {
        response = apiService.getTopRated()
        return responseHandle(response)
    }

    suspend fun getDetail(tvId: Int): Flow<ApiResponse<TvItemsResponse>> {
        val response = apiService.getDetailTvShow(tvId)
        isIdEqualsToZero = response.id == 0
        return detailResponseHandle(response)
    }

    suspend fun searchTvShow(query: String?): Flow<ApiResponse<List<TvItemsResponse>>> {
        response = apiService.searchTvShow(query)
        return responseHandle(response)
    }

    suspend fun getCreditsTvShow(tvId: Int): Flow<ApiResponse<List<CastResponse>>> {
        creditsResponse = apiService.getCreditsTvShow(tvId)
        return creditsResponseHandle(creditsResponse)
    }
}