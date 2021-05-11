package com.rasyidin.myfilmlist.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rasyidin.myfilmlist.core.data.source.local.entity.MovieEntity
import com.rasyidin.myfilmlist.core.data.source.local.entity.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyFilmDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getTvShowDao(): TvShowDao
}