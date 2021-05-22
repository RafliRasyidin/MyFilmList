package com.rasyidin.myfilmlist.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.rasyidin.myfilmlist.core.data.source.local.entity.tvshow.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM airing_today_tv_show")
    fun getAllAiringTodayTvShow(): Flow<List<AiringTodayTvShowEntity>>

    @Query("SELECT * FROM on_the_air_tv_show")
    fun getAllOnTheAirTvShow(): Flow<List<OnTheAirTvShowEntity>>

    @Query("SELECT * FROM popular_tv_show")
    fun getAllPopularTvShow(): Flow<List<PopularTvShowEntity>>

    @Query("SELECT * FROM top_rated_tv_show")
    fun getAllTopRatedTvShow(): Flow<List<TopRatedTvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAiringTodayTvShow(entity: List<AiringTodayTvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOnTheAirTvShow(entity: List<OnTheAirTvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPopularTvShow(entity: List<PopularTvShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTopRatedTvShow(entity: List<TopRatedTvShowEntity>)

    @Query("DELETE FROM airing_today_tv_show")
    suspend fun deleteAllAiringTodayTvShow()

    @Query("DELETE FROM on_the_air_tv_show")
    suspend fun deleteAllOnTheAirTvShow()

    @Query("DELETE FROM popular_tv_show")
    suspend fun deleteAllPopularTvShow()

    @Query("DELETE FROM top_rated_tv_show")
    suspend fun deleteAllTopRatedTvShow()


    @Query("SELECT * FROM fav_tv_show")
    fun getFavTvShow(): DataSource.Factory<Int, FavTvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavTvShow(favTvShowEntity: FavTvShowEntity)

    @Delete
    suspend fun removeFavTvShow(tvShowEntity: FavTvShowEntity)

    @Query("SELECT EXISTS(SELECT * FROM fav_tv_show WHERE id = :id)")
    fun isFavorited(id: Int): Flow<Boolean>
}