package com.rasyidin.myfilmlist.core.data.source.local.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_rated_movie")
data class TopRatedMovieEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String? = "",

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = "",

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double = 0.0
)
