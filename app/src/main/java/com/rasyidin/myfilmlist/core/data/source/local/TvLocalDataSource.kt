package com.rasyidin.myfilmlist.core.data.source.local

import com.rasyidin.myfilmlist.core.data.source.local.entity.TvShowEntity
import com.rasyidin.myfilmlist.core.data.source.local.room.TvShowDao

class TvLocalDataSource(private val tvShowDao: TvShowDao) {

    fun getFavTvShow() = tvShowDao.getFavTvShow()

    suspend fun setFavTvShow(tvShowEntity: TvShowEntity) = tvShowDao.setFavTvShow(tvShowEntity)

    suspend fun removeFavTvShow(tvShowEntity: TvShowEntity) =
        tvShowDao.removeFavTvShow(tvShowEntity)

    fun isFavorited(id: Int) = tvShowDao.isFavorited(id)


}