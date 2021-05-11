package com.rasyidin.myfilmlist.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.rasyidin.myfilmlist.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM fav_tv_show")
    fun getFavTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavTvShow(tvShowEntity: TvShowEntity)

    @Delete
    suspend fun removeFavTvShow(tvShowEntity: TvShowEntity)

    @Query("SELECT EXISTS(SELECT * FROM fav_tv_show WHERE id = :id)")
    fun isFavorited(id: Int): Flow<Boolean>
}