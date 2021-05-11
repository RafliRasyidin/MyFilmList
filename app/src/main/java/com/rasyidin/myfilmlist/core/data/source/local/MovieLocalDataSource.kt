package com.rasyidin.myfilmlist.core.data.source.local

import com.rasyidin.myfilmlist.core.data.source.local.entity.MovieEntity
import com.rasyidin.myfilmlist.core.data.source.local.room.MovieDao

class MovieLocalDataSource(private val movieDao: MovieDao) {

    fun getFavMovies() = movieDao.getFavMovies()

    suspend fun setFavMovie(movieEntity: MovieEntity) = movieDao.setFavMovie(movieEntity)

    suspend fun removeFavMovie(movieEntity: MovieEntity) = movieDao.removeFavMovie(movieEntity)

    fun isFavorited(id: Int) = movieDao.isFavorited(id)

}
