package com.rasyidin.myfilmlist.core.data.source.local

import com.rasyidin.myfilmlist.core.data.source.local.entity.tvshow.*
import com.rasyidin.myfilmlist.core.data.source.local.room.MyFilmDatabase
import com.rasyidin.myfilmlist.core.data.source.local.room.TvShowDao
import org.koin.java.KoinJavaComponent.inject

class TvLocalDataSource(val filmDatabase: MyFilmDatabase) {

    private val tvShowDao: TvShowDao by inject(TvShowDao::class.java)

    fun getAllAiringTodayTvShow() = tvShowDao.getAllAiringTodayTvShow()

    fun getAllOnTheAirTvShow() = tvShowDao.getAllOnTheAirTvShow()

    fun getAllPopularTvShow() = tvShowDao.getAllPopularTvShow()

    fun getAllTopRatedTvShow() = tvShowDao.getAllTopRatedTvShow()

    suspend fun insertAll(entity: List<AiringTodayTvShowEntity>) =
        tvShowDao.insertAllAiringTodayTvShow(entity)

    @JvmName("insertAllOnTheAirTvShow")
    suspend fun insertAll(entity: List<OnTheAirTvShowEntity>) =
        tvShowDao.insertAllOnTheAirTvShow(entity)

    @JvmName("insertAllPopular")
    suspend fun insertAll(entity: List<PopularTvShowEntity>) =
        tvShowDao.insertAllPopularTvShow(entity)

    @JvmName("insertAllTopRated")
    suspend fun insertAll(entity: List<TopRatedTvShowEntity>) =
        tvShowDao.insertAllTopRatedTvShow(entity)

    suspend fun deleteAllAiringTodayTvShow() = tvShowDao.deleteAllAiringTodayTvShow()

    suspend fun deleteAllOnTheAirTvShow() = tvShowDao.deleteAllOnTheAirTvShow()

    suspend fun deleteAllPopularTvShow() = tvShowDao.deleteAllPopularTvShow()

    suspend fun deleteAllTopRatedTvShow() = tvShowDao.deleteAllTopRatedTvShow()

    fun getFavTvShow() = tvShowDao.getFavTvShow()

    suspend fun setFavTvShow(favTvShowEntity: FavTvShowEntity) =
        tvShowDao.setFavTvShow(favTvShowEntity)

    suspend fun removeFavTvShow(favTvShowEntity: FavTvShowEntity) =
        tvShowDao.removeFavTvShow(favTvShowEntity)

    fun isFavorited(id: Int) = tvShowDao.isFavorited(id)


}