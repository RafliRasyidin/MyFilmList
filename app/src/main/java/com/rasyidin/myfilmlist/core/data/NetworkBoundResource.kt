package com.rasyidin.myfilmlist.core.data

import android.util.Log
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.utils.IdlingResource
import kotlinx.coroutines.flow.*


inline fun <ResultType, RequestType> NetworkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> Flow<ApiResponse<RequestType>>,
    crossinline saveCallResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean
) = flow {
    IdlingResource.increment()
    val dbSource = query().first()

    val flow =
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val response = fetch().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(response.data)
                    query().map { Resource.Success(it) }
                }
                is ApiResponse.Empty -> {
                    query().map { data ->
                        Resource.Success(data)
                    }
                }
                is ApiResponse.Error -> {
                    query().map { Resource.Error(null, response.message) }
                }
            }
        } else {
            query().map { data ->
                Resource.Success(data)
            }
        }
    IdlingResource.decrement()
    emitAll(flow)
}.onCompletion {
    Log.d("NetworkBoundResource", "Complete")
}