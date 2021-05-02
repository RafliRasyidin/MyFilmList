package com.rasyidin.myfilmlist.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @field:SerializedName("cast")
    val data: List<CastResponse>
)