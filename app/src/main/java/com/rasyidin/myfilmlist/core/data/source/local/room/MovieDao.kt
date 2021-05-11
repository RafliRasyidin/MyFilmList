package com.rasyidin.myfilmlist.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.rasyidin.myfilmlist.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM fav_movie")
    fun getFavMovies(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun removeFavMovie(movieEntity: MovieEntity)

    @Query("SELECT EXISTS(SELECT * FROM fav_movie WHERE id = :id)")
    fun isFavorited(id: Int): Flow<Boolean>
}