package com.rasyidin.myfilmlist.core.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.data.source.local.MovieLocalDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.MoviesRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.Person
import com.rasyidin.myfilmlist.core.domain.repository.IMoviesRepository
import com.rasyidin.myfilmlist.core.utils.toListMovie
import com.rasyidin.myfilmlist.core.utils.toMovie
import com.rasyidin.myfilmlist.core.utils.toMovieEntity
import com.rasyidin.myfilmlist.core.utils.toPerson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

@Suppress("UNCHECKED_CAST")
class MoviesRepository(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : IMoviesRepository {

    override fun getNowPlaying(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getNowPlaying().collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toListMovie()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<Movie>>(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<Movie>>>
    }

    override fun getPopular(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getPopular().collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toListMovie()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<Movie>>(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<Movie>>>
    }

    override fun getTopRated(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getTopRated().collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toListMovie()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<Movie>>(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<Movie>>>
    }

    override fun getUpComing(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getUpcoming().collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toListMovie()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<Movie>>(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<Movie>>>
    }

    override fun searchMovies(query: String?): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            when (val response = remoteDataSource.searchMovie(query).first()) {
                is ApiResponse.Success -> {
                    val result = response.data.toListMovie()
                    emit(Resource.Success(result))
                }
                is ApiResponse.Empty -> emit(Resource.Success<List<Movie>>(emptyList()))
                is ApiResponse.Error -> emit(Resource.Error(null, response.message))
            }
        } as Flow<Resource<List<Movie>>>
    }

    override fun getDetail(movieId: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getDetail(movieId).collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toMovie()))
                    is ApiResponse.Empty -> emit(Resource.Success<Movie>(null))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<Movie>>
    }

    override fun getCreditsMovie(movieId: Int): Flow<Resource<List<Person>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getCreditsMovie(movieId).collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toPerson()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<Person>>(null))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<Person>>>
    }

    override fun getFavMovies(): LiveData<PagedList<Movie>> {
        val dataSource = localDataSource.getFavMovies().map {
            it.toMovie()
        }

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()

        return LivePagedListBuilder(dataSource, config).build()
    }

    override suspend fun setFavMovie(movie: Movie) {
        val movieEntity = movie.toMovieEntity()
        return localDataSource.setFavMovie(movieEntity)
    }

    override suspend fun removeFavMovie(movie: Movie) {
        val movieEntity = movie.toMovieEntity()
        return localDataSource.removeFavMovie(movieEntity)
    }

    override fun isFavorited(id: Int): Flow<Boolean> {
        return localDataSource.isFavorited(id)
    }

}