package com.rasyidin.myfilmlist.core.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.withTransaction
import com.rasyidin.myfilmlist.core.data.NetworkBoundResource
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.data.source.local.MovieLocalDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.MoviesRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.Person
import com.rasyidin.myfilmlist.core.domain.repository.IMoviesRepository
import com.rasyidin.myfilmlist.core.utils.*
import com.rasyidin.myfilmlist.core.utils.Constants.TIME_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@Suppress("UNCHECKED_CAST")
class MoviesRepository(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : IMoviesRepository {

    /*private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setPageSize(LOAD_PER_PAGE)
        .build()*/

    /*override fun getAllNowPlaying(): Flow<Resource<PagedList<Movie>>> {
        val dataSource = remoteDataSource.getAllNowPlaying().map { it.toMovie() }
        return handleResource(dataSource)
    }

    override fun getAllPopular(): Flow<Resource<PagedList<Movie>>> {
        val dataSource = remoteDataSource.getAllPopular().map { it.toMovie() }
        return handleResource(dataSource)
    }

    override fun getAllTopRated(): Flow<Resource<PagedList<Movie>>> {
        val dataSource = remoteDataSource.getAllTopRated().map { it.toMovie() }
        return handleResource(dataSource)
    }

    override fun getAllUpComing(): Flow<Resource<PagedList<Movie>>> {
        val dataSource = remoteDataSource.getAllUpComing().map { it.toMovie() }
        return handleResource(dataSource)
    }*/

    override fun getNowPlaying(): Flow<Resource<List<Movie>>> {
        return NetworkBoundResource(
            query = {
                localDataSource.getAllNowPlayingMovie().map {
                    it.toListMovie()
                }
            },

            fetch = {
                delay(TIME_DELAY)
                remoteDataSource.getNowPlaying()
            },

            saveCallResult = { movies ->
                localDataSource.filmDatabase.withTransaction {
                    localDataSource.deleteAllNowPlayingMovie()
                    localDataSource.insertAll(movies.toNowPlayingEntity())
                }
            },

            shouldFetch = { data ->
                data.isEmpty()
            }
        ) as Flow<Resource<List<Movie>>>
    }

    override fun getPopular(): Flow<Resource<List<Movie>>> {
        return NetworkBoundResource(
            query = {
                localDataSource.getAllPopularMovie().map {
                    it.toListMovie()
                }
            },

            fetch = {
                delay(TIME_DELAY)
                remoteDataSource.getPopular()
            },

            saveCallResult = { movies ->
                localDataSource.filmDatabase.withTransaction {
                    localDataSource.deleteAllPopularMovie()
                    localDataSource.insertAll(movies.toPopularEntity())
                }
            },

            shouldFetch = { data ->
                data.isEmpty()
            }
        ) as Flow<Resource<List<Movie>>>

    }

    override fun getTopRated(): Flow<Resource<List<Movie>>> {
        return NetworkBoundResource(
            query = {
                localDataSource.getAllTopRatedMovie().map {
                    it.toListMovie()
                }
            },

            fetch = {
                delay(TIME_DELAY)
                remoteDataSource.getTopRated()
            },

            saveCallResult = { movies ->
                localDataSource.filmDatabase.withTransaction {
                    localDataSource.deleteAllTopRatedMovie()
                    localDataSource.insertAll(movies.toTopRatedEntity())
                }
            },

            shouldFetch = { data ->
                data.isEmpty()
            }
        ) as Flow<Resource<List<Movie>>>
    }

    override fun getUpComing(): Flow<Resource<List<Movie>>> {
        return NetworkBoundResource(
            query = {
                localDataSource.getAllUpComingMovie().map {
                    it.toListMovie()
                }
            },

            fetch = {
                delay(TIME_DELAY)
                remoteDataSource.getUpcoming()
            },

            saveCallResult = { movies ->
                localDataSource.filmDatabase.withTransaction {
                    localDataSource.deleteAllUpComingMovie()
                    localDataSource.insertAll(movies.toUpComingEntity())
                }
            },

            shouldFetch = { data ->
                data.isEmpty()
            }
        ) as Flow<Resource<List<Movie>>>

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

    /*private fun handleResource(
        dataSource: DataSource.Factory<Int, Movie>
    ) = flow {
        IdlingResource.increment()
        emit(Resource.Loading())
        val pagedList = LivePagedListBuilder(dataSource, config).build()
        pagedList.asFlow().collect { data ->
            emit(Resource.Success(data))
            IdlingResource.decrement()
        }
    }.catch { e ->
        IdlingResource.decrement()
        emit(Resource.Error(message = e.toString()))
    }.flowOn(Dispatchers.IO)*/

}