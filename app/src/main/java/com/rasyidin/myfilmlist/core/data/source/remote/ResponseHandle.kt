package com.rasyidin.myfilmlist.core.data.source.remote

import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.utils.IdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class ResponseHandle<T> {

    protected var isIdEqualsToZero: Boolean? = null

    protected lateinit var response: BaseResponse<T>

    protected fun responseHandle(response: BaseResponse<T>): Flow<ApiResponse<List<T>>> {
        IdlingResource.increment()
        return flow {
            val data = response.data
            if (data.isNotEmpty()) {
                emit(ApiResponse.Success(data))
                IdlingResource.decrement()
            } else {
                emit(ApiResponse.Empty)
                IdlingResource.decrement()
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.message.toString()))
            IdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }

    protected fun detailResponseHandle(response: T): Flow<ApiResponse<T>> {
        IdlingResource.increment()
        return flow {
            if (!isIdEqualsToZero!!) {
                emit(ApiResponse.Success(response))
                IdlingResource.decrement()
            } else {
                emit(ApiResponse.Empty)
                IdlingResource.decrement()
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.message.toString()))
            IdlingResource.decrement()
        }.flowOn(Dispatchers.IO)
    }
}