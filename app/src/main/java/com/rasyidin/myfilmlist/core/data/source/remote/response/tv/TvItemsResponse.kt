package com.rasyidin.myfilmlist.core.data.source.remote.response.tv

import com.google.gson.annotations.SerializedName
import com.rasyidin.myfilmlist.core.data.source.remote.response.Genre

data class TvItemsResponse(
    @field:SerializedName("backdrop_path")
    val backdropPath: String? = "",

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = "",

    val genres: List<Genre>,

    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("name")
    val name: String? = "",

    @field:SerializedName("overview")
    val overview: String? = "",

    @field:SerializedName("popularity")
    val popularity: Double = 0.0,

    @field:SerializedName("poster_path")
    val posterPath: String? = "",

    @field:SerializedName("vote_count")
    val voteCount: Int = 0,

    @field:SerializedName("vote_average")
    val voteAverage: Double = 0.0
)