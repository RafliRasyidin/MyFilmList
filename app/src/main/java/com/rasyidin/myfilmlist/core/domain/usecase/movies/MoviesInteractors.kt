package com.rasyidin.myfilmlist.core.domain.usecase.movies

import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.Person
import com.rasyidin.myfilmlist.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesInteractors(private val moviesRepository: IMoviesRepository): IMoviesUseCase {

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
}