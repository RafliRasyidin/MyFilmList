package com.rasyidin.myfilmlist.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rasyidin.myfilmlist.core.data.source.local.entity.movie.*
import com.rasyidin.myfilmlist.core.data.source.local.entity.tvshow.*

@Database(
    entities = [
        FavMovieEntity::class,
        NowPlayingMovieEntity::class,
        PopularMovieEntity::class,
        TopRatedMovieEntity::class,
        UpComingMovieEntity::class,
        FavTvShowEntity::class,
        AiringTodayTvShowEntity::class,
        OnTheAirTvShowEntity::class,
        PopularTvShowEntity::class,
        TopRatedTvShowEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MyFilmDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getTvShowDao(): TvShowDao
}