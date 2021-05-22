package com.rasyidin.myfilmlist.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.rasyidin.myfilmlist.core.data.source.local.entity.movie.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM now_playing_movie")
    fun getAllNowPlayingMovie(): Flow<List<NowPlayingMovieEntity>>

    @Query("SELECT * FROM popular_movie")
    fun getAllPopularMovie(): Flow<List<PopularMovieEntity>>

    @Query("SELECT * FROM top_rated_movie")
    fun getAllTopRatedMovie(): Flow<List<TopRatedMovieEntity>>

    @Query("SELECT * FROM up_coming_movie")
    fun getAllUpComingMovie(): Flow<List<UpComingMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNowPlayingMovie(entity: List<NowPlayingMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPopularMovie(entity: List<PopularMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTopRatedMovie(entity: List<TopRatedMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUpComingMovie(entity: List<UpComingMovieEntity>)

    @Query("DELETE FROM now_playing_movie")
    suspend fun deleteAllNowPlayingMovie()

    @Query("DELETE FROM popular_movie")
    suspend fun deleteAllPopularMovie()

    @Query("DELETE FROM top_rated_movie")
    suspend fun deleteAllTopRatedMovie()

    @Query("DELETE FROM up_coming_movie")
    suspend fun deleteAllUpComingMovie()


    @Query("SELECT * FROM fav_movie")
    fun getFavMovies(): DataSource.Factory<Int, FavMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavMovie(favMovieEntity: FavMovieEntity)

    @Delete
    suspend fun removeFavMovie(movieEntity: FavMovieEntity)

    @Query("SELECT EXISTS(SELECT * FROM fav_movie WHERE id = :id)")
    fun isFavorited(id: Int): Flow<Boolean>
}