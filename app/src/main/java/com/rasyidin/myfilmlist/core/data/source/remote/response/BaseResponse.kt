package com.rasyidin.myfilmlist.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse

data class BaseResponse<T>(
    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val data: List<T>,
)