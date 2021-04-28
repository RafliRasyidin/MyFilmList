package com.rasyidin.myfilmlist.core.domain.model

import com.rasyidin.myfilmlist.core.data.source.remote.response.Genre

data class TvShow(

    val backdropPath: String? = "",
    val firstAirDate: String? = "",
    val genres: List<Genre>,
    val id: Int = 0,
    val name: String? = "",
    val overview: String? = "",
    val popularity: Double = 0.0,
    val posterPath: String? = "",
    val voteCount: Int = 0,
    val voteAverage: Double = 0.0
)