package com.rasyidin.myfilmlist.core.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.data.source.local.TvLocalDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.TvShowRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.domain.model.Person
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.core.domain.repository.ITvShowRepository
import com.rasyidin.myfilmlist.core.utils.toListTvShow
import com.rasyidin.myfilmlist.core.utils.toPerson
import com.rasyidin.myfilmlist.core.utils.toTvShow
import com.rasyidin.myfilmlist.core.utils.toTvShowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

@Suppress("UNCHECKED_CAST")
class TvShowRepository(
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: TvLocalDataSource
) : ITvShowRepository {

    override fun getAiringToday(): Flow<Resource<List<TvShow>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getAiringToday().collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toListTvShow()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<TvShow>>(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<TvShow>>>
    }

    override fun getOnTheAir(): Flow<Resource<List<TvShow>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getOnTheAir().collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toListTvShow()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<TvShow>>(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<TvShow>>>
    }

    override fun getTopRated(): Flow<Resource<List<TvShow>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getTopRated().collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toListTvShow()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<TvShow>>(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<TvShow>>>
    }

    override fun getPopular(): Flow<Resource<List<TvShow>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getPopular().collect { response ->
                when (response) {
                    is ApiResponse.Success -> emit(Resource.Success(response.data.toListTvShow()))
                    is ApiResponse.Empty -> emit(Resource.Success<List<TvShow>>(emptyList()))
                    is ApiResponse.Error -> emit(Resource.Error(null, response.message))
                }
            }
        } as Flow<Resource<List<TvShow>>>
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

}