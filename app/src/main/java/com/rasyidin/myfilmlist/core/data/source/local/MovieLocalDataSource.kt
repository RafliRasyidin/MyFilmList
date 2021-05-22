package com.rasyidin.myfilmlist.core.data.source.local

import com.rasyidin.myfilmlist.core.data.source.local.entity.movie.*
import com.rasyidin.myfilmlist.core.data.source.local.room.MovieDao
import com.rasyidin.myfilmlist.core.data.source.local.room.MyFilmDatabase
import org.koin.java.KoinJavaComponent.inject

class MovieLocalDataSource(val filmDatabase: MyFilmDatabase) {

    private val movieDao: MovieDao by inject(MovieDao::class.java)

    fun getAllNowPlayingMovie() = movieDao.getAllNowPlayingMovie()

    fun getAllPopularMovie() = movieDao.getAllPopularMovie()

    fun getAllTopRatedMovie() = movieDao.getAllTopRatedMovie()

    fun getAllUpComingMovie() = movieDao.getAllUpComingMovie()

    suspend fun insertAll(entity: List<NowPlayingMovieEntity>) =
        movieDao.insertAllNowPlayingMovie(entity)

    @JvmName("insertAllPopularMovie")
    suspend fun insertAll(entity: List<PopularMovieEntity>) =
        movieDao.insertAllPopularMovie(entity)

    @JvmName("insertAllTopRatedMovie")
    suspend fun insertAll(entity: List<TopRatedMovieEntity>) =
        movieDao.insertAllTopRatedMovie(entity)


    @JvmName("insertAllUpComingMovie")
    suspend fun insertAll(entity: List<UpComingMovieEntity>) =
        movieDao.insertAllUpComingMovie(entity)

    suspend fun deleteAllNowPlayingMovie() = movieDao.deleteAllNowPlayingMovie()

    suspend fun deleteAllPopularMovie() = movieDao.deleteAllPopularMovie()

    suspend fun deleteAllTopRatedMovie() = movieDao.deleteAllTopRatedMovie()

    suspend fun deleteAllUpComingMovie() = movieDao.deleteAllUpComingMovie()

    fun getFavMovies() = movieDao.getFavMovies()

    suspend fun setFavMovie(favMovieEntity: FavMovieEntity) = movieDao.setFavMovie(favMovieEntity)

    suspend fun removeFavMovie(favMovieEntity: FavMovieEntity) =
        movieDao.removeFavMovie(favMovieEntity)

    fun isFavorited(id: Int) = movieDao.isFavorited(id)

}
