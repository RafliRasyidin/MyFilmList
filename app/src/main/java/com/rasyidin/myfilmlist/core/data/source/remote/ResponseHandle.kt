package com.rasyidin.myfilmlist.core.data.source.remote

import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.CastResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.CreditsResponse
import com.rasyidin.myfilmlist.core.utils.IdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class ResponseHandle<T> {

    protected var isIdEqualsToZero: Boolean = true

    protected lateinit var response: BaseResponse<T>

    protected lateinit var creditsResponse: CreditsResponse

    protected fun responseHandle(response: BaseResponse<T>): Flow<ApiResponse<List<T>>> {
        IdlingResource.increment()
        return flow {
            val data = response.data
            if (data.isNotEmpty()) {
                emit(ApiResponse.Success(data))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.message.toString()))
        }.onCompletion {
            IdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }

    protected fun detailResponseHandle(response: T): Flow<ApiResponse<T>> {
        IdlingResource.increment()
        return flow {
            if (!isIdEqualsToZero) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.message.toString()))
        }.onCompletion {
            IdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }

    protected fun creditsResponseHandle(response: CreditsResponse): Flow<ApiResponse<List<CastResponse>>> {
        IdlingResource.increment()
        return flow {
            val data = response.data
            if (data.isNotEmpty()) {
                emit(ApiResponse.Success(data))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.message.toString()))
        }.onCompletion {
            IdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }
}