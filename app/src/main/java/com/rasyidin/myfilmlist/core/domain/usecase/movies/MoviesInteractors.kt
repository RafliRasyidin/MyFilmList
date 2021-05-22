package com.rasyidin.myfilmlist.core.domain.usecase.movies

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.Person
import com.rasyidin.myfilmlist.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesInteractors(private val moviesRepository: IMoviesRepository): IMoviesUseCase {
    /*override fun getAllNowPlaying(): Flow<Resource<PagedList<Movie>>> {
        return moviesRepository.getAllNowPlaying()
    }

    override fun getAllPopular(): Flow<Resource<PagedList<Movie>>> {
        return moviesRepository.getAllPopular()
    }

    override fun getAllTopRated(): Flow<Resource<PagedList<Movie>>> {
        return moviesRepository.getAllTopRated()
    }

    override fun getAllUpComing(): Flow<Resource<PagedList<Movie>>> {
        return moviesRepository.getAllUpComing()
    }*/

    override fun getNowPlaying(): Flow<Resource<List<Movie>>> {
        return moviesRepository.getNowPlaying()
    }

    override fun getPopular(): Flow<Resource<List<Movie>>> {
        return moviesRepository.getPopular()
    }

    override fun getTopRated(): Flow<Resource<List<Movie>>> {
        return moviesRepository.getTopRated()
    }

    override fun getUpComing(): Flow<Resource<List<Movie>>> {
        return moviesRepository.getUpComing()
    }

    override fun searchMovies(query: String?): Flow<Resource<List<Movie>>> {
        return moviesRepository.searchMovies(query)
    }

    override fun getDetail(movieId: Int): Flow<Resource<Movie>> {
        return moviesRepository.getDetail(movieId)
    }

    override fun getCreditMovie(movieId: Int): Flow<Resource<List<Person>>> {
        return moviesRepository.getCreditsMovie(movieId)
    }

    override fun getFavMovies(): LiveData<PagedList<Movie>> {
        return moviesRepository.getFavMovies()
    }

    override suspend fun setFavMovie(movie: Movie) {
        return moviesRepository.setFavMovie(movie)
    }

    override suspend fun removeFavMovie(movie: Movie) {
        return moviesRepository.removeFavMovie(movie)
    }

    override fun isFavorited(id: Int): Flow<Boolean> {
        return moviesRepository.isFavorited(id)
    }
}