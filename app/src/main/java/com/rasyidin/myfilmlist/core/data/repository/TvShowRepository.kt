package com.rasyidin.myfilmlist.core.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.withTransaction
import com.rasyidin.myfilmlist.core.data.NetworkBoundResource
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.data.source.local.TvLocalDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.TvShowRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.domain.model.Person
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.core.domain.repository.ITvShowRepository
import com.rasyidin.myfilmlist.core.utils.*
import com.rasyidin.myfilmlist.core.utils.Constants.TIME_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@Suppress("UNCHECKED_CAST")
class TvShowRepository(
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: TvLocalDataSource
) : ITvShowRepository {

    /*private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setPageSize(Constants.LOAD_PER_PAGE)
        .build()

    override fun getAllAiringToday(): Flow<Resource<PagedList<TvShow>>> {
        val dataSource = remoteDataSource.getAllAiringToday().map { it.toTvShow() }
        return handleResource(dataSource)
    }

    override fun getAllOnTheAir(): Flow<Resource<PagedList<TvShow>>> {
        val dataSource = remoteDataSource.getAllOnTheAir().map { it.toTvShow() }
        return handleResource(dataSource)
    }

    override fun getAllPopular(): Flow<Resource<PagedList<TvShow>>> {
        val dataSource = remoteDataSource.getAllPopular().map { it.toTvShow() }
        return handleResource(dataSource)
    }

    override fun getAllTopRated(): Flow<Resource<PagedList<TvShow>>> {
        val dataSource = remoteDataSource.getAllTopRated().map { it.toTvShow() }
        return handleResource(dataSource)
    }*/

    override fun getAiringToday(): Flow<Resource<List<TvShow>>> {
        return NetworkBoundResource(
            query = {
                localDataSource.getAllAiringTodayTvShow().map {
                    it.toListTvShow()
                }
            },

            fetch = {
                delay(TIME_DELAY)
                remoteDataSource.getAiringToday()
            },

            saveCallResult = { movies ->
                localDataSource.filmDatabase.withTransaction {
                    localDataSource.deleteAllAiringTodayTvShow()
                    localDataSource.insertAll(movies.toAiringTodayEntity())
                }
            },

            shouldFetch = { data ->
                data.isEmpty()
            }
        ) as Flow<Resource<List<TvShow>>>
    }

    override fun getOnTheAir(): Flow<Resource<List<TvShow>>> {
        return NetworkBoundResource(
            query = {
                localDataSource.getAllOnTheAirTvShow().map {
                    it.toListTvShow()
                }
            },

            fetch = {
                delay(TIME_DELAY)
                remoteDataSource.getOnTheAir()
            },

            saveCallResult = { movies ->
                localDataSource.filmDatabase.withTransaction {
                    localDataSource.deleteAllOnTheAirTvShow()
                    localDataSource.insertAll(movies.toOnTheAirEntity())
                }
            },

            shouldFetch = { data ->
                data.isEmpty()
            }
        ) as Flow<Resource<List<TvShow>>>
    }

    override fun getTopRated(): Flow<Resource<List<TvShow>>> {
        return NetworkBoundResource(
            query = {
                localDataSource.getAllTopRatedTvShow().map {
                    it.toListTvShow()
                }
            },

            fetch = {
                delay(TIME_DELAY)
                remoteDataSource.getTopRated()
            },

            saveCallResult = { movies ->
                localDataSource.filmDatabase.withTransaction {
                    localDataSource.deleteAllTopRatedTvShow()
                    localDataSource.insertAll(movies.toTopRatedEntity())
                }
            },

            shouldFetch = { data ->
                data.isEmpty()
            }
        ) as Flow<Resource<List<TvShow>>>
    }

    override fun getPopular(): Flow<Resource<List<TvShow>>> {
        return NetworkBoundResource(
            query = {
                localDataSource.getAllPopularTvShow().map {
                    it.toListTvShow()
                }
            },

            fetch = {
                delay(TIME_DELAY)
                remoteDataSource.getPopular()
            },

            saveCallResult = { movies ->
                localDataSource.filmDatabase.withTransaction {
                    localDataSource.deleteAllPopularTvShow()
                    localDataSource.insertAll(movies.toPopularEntity())
                }
            },

            shouldFetch = { data ->
                data.isEmpty()
            }
        ) as Flow<Resource<List<TvShow>>>
    }

    override fun searchTvShow(query: String?): Flow<Resource<List<TvShow>>> {
        return flow {
            emit(Resource.Loading())
            when (val response = remoteDataSource.searchTvShow(query).first()) {
                is ApiResponse.Success -> {
                    val result = response.data.toListTvShow()
                    emit(Resource.Success(result))
                }
                is ApiResponse.Empty -> emit(Resource.Success<List<TvShow>>(emptyList()))
                is ApiResponse.Error -> emit(Resource.Error(null, response.message))
            }
        } as Flow<Resource<List<TvShow>>>
    }

    override fun getDetail(tvId: Int): Flow<Resource<TvShow>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getDetail(tvId).collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toTvShow()))
                    is ApiResponse.Empty -> emit(Resource.Success<TvShow>(null))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<TvShow>>
    }

    override fun getCreditsTvShow(tvId: Int): Flow<Resource<List<Person>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getCreditsTvShow(tvId).collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toPerson()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<Person>>(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<Person>>>
    }

    override fun getFavTvShow(): LiveData<PagedList<TvShow>> {
        val dataSource = localDataSource.getFavTvShow().map {
            it.toTvShow()
        }

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()

        return LivePagedListBuilder(dataSource, config).build()
    }

    override suspend fun setFavTvShow(tvShow: TvShow) {
        val tvShowEntity = tvShow.toTvShowEntity()
        return localDataSource.setFavTvShow(tvShowEntity)
    }

    override suspend fun removeFavTvShow(tvShow: TvShow) {
        val tvShowEntity = tvShow.toTvShowEntity()
        return localDataSource.removeFavTvShow(tvShowEntity)
    }

    override fun isFavorited(id: Int): Flow<Boolean> {
        return localDataSource.isFavorited(id)
    }

    /*private fun handleResource(
        dataSource: DataSource.Factory<Int, TvShow>
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