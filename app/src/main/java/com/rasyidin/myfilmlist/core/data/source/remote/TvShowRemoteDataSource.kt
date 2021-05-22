package com.rasyidin.myfilmlist.core.data.source.remote

import androidx.paging.DataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.network.TvApiService
import com.rasyidin.myfilmlist.core.data.source.remote.paging.TvShowDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.CastResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class TvShowRemoteDataSource(
    private val apiService: TvApiService,
    private val coroutineScope: CoroutineScope
) : ResponseHandle<TvItemsResponse>() {

    fun getAllAiringToday(): DataSource.Factory<Int, TvItemsResponse> =
        object : DataSource.Factory<Int, TvItemsResponse>() {
            override fun create(): DataSource<Int, TvItemsResponse> {
                return object : TvShowDataSource(coroutineScope) {
                    override val fetch: suspend (Int) -> BaseResponse<TvItemsResponse>
                        get() = { page ->
                            apiService.getAiringToday(page = page)
                        }
                }
            }
        }

    fun getAllOnTheAir(): DataSource.Factory<Int, TvItemsResponse> =
        object : DataSource.Factory<Int, TvItemsResponse>() {
            override fun create(): DataSource<Int, TvItemsResponse> {
                return object : TvShowDataSource(coroutineScope) {
                    override val fetch: suspend (Int) -> BaseResponse<TvItemsResponse>
                        get() = { page ->
                            apiService.getOnTheAir(page = page)
                        }
                }
            }
        }

    fun getAllPopular(): DataSource.Factory<Int, TvItemsResponse> =
        object : DataSource.Factory<Int, TvItemsResponse>() {
            override fun create(): DataSource<Int, TvItemsResponse> {
                return object : TvShowDataSource(coroutineScope) {
                    override val fetch: suspend (Int) -> BaseResponse<TvItemsResponse>
                        get() = { page ->
                            apiService.getPopular(page = page)
                        }
                }
            }
        }

    fun getAllTopRated(): DataSource.Factory<Int, TvItemsResponse> =
        object : DataSource.Factory<Int, TvItemsResponse>() {
            override fun create(): DataSource<Int, TvItemsResponse> {
                return object : TvShowDataSource(coroutineScope) {
                    override val fetch: suspend (Int) -> BaseResponse<TvItemsResponse>
                        get() = { page ->
                            apiService.getTopRated(page = page)
                        }
                }
            }
        }

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