package com.rasyidin.myfilmlist.core.data.source.remote.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse
import com.rasyidin.myfilmlist.core.utils.Constants.FIRST_PAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class TvShowDataSource(private val coroutineScope: CoroutineScope) :
    PageKeyedDataSource<Int, TvItemsResponse>() {

    private var page = FIRST_PAGE

    abstract val fetch: suspend (Int) -> BaseResponse<TvItemsResponse>

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvItemsResponse>
    ) {
        try {
            coroutineScope.launch {
                callback.onResult(fetch(page).data, null, page + 1)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TvItemsResponse>
    ) {
        try {
            coroutineScope.launch {
                if (fetch(params.key).page >= params.key) {
                    callback.onResult(fetch(params.key).data, params.key + 1)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvItemsResponse>) {

    }

    companion object {
        private val TAG = TvShowDataSource::class.java.simpleName
    }
}