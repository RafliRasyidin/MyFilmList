package com.rasyidin.myfilmlist.core.data.source.local.entity.tvshow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "on_the_air_tv_show")
data class OnTheAirTvShowEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val name: String? = "",

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = "",

    @ColumnInfo(name = "rating")
    val voteAverage: Double = 0.0
)