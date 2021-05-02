package com.rasyidin.myfilmlist.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CastResponse(
    @field:SerializedName("character")
    val character: String? = "",

    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("name")
    val name: String? = "",

    @field:SerializedName("popularity")
    val popularity: Double = 0.0,

    @field:SerializedName("profile_path")
    val profilePath: String? = ""
)