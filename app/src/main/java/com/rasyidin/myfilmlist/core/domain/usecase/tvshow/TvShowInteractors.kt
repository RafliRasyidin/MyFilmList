package com.rasyidin.myfilmlist.core.domain.usecase.tvshow

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.Person
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.core.domain.repository.ITvShowRepository
import kotlinx.coroutines.flow.Flow

class TvShowInteractors(private val tvShowRepository: ITvShowRepository): ITvShowUseCase {
    /*override fun getAllAiringToday(): Flow<Resource<PagedList<TvShow>>> {
        return tvShowRepository.getAllAiringToday()
    }

    override fun getAllOnTheAir(): Flow<Resource<PagedList<TvShow>>> {
        return tvShowRepository.getAllOnTheAir()
    }

    override fun getAllTopRated(): Flow<Resource<PagedList<TvShow>>> {
        return tvShowRepository.getAllTopRated()
    }

    override fun getAllPopular(): Flow<Resource<PagedList<TvShow>>> {
        return tvShowRepository.getAllPopular()
    }*/

    override fun getAiringToday(): Flow<Resource<List<TvShow>>> {
        return tvShowRepository.getAiringToday()
    }

    override fun getOnTheAir(): Flow<Resource<List<TvShow>>> {
        return tvShowRepository.getOnTheAir()
    }

    override fun getTopRated(): Flow<Resource<List<TvShow>>> {
        return tvShowRepository.getTopRated()
    }

    override fun getPopular(): Flow<Resource<List<TvShow>>> {
        return tvShowRepository.getPopular()
    }

    override fun searchTvShow(query: String?): Flow<Resource<List<TvShow>>> {
        return tvShowRepository.searchTvShow(query)
    }

    override fun getDetail(tvId: Int): Flow<Resource<TvShow>> {
        return tvShowRepository.getDetail(tvId)
    }

    override fun getCreditsTvShow(tvId: Int): Flow<Resource<List<Person>>> {
        return tvShowRepository.getCreditsTvShow(tvId)
    }

    override fun getFavTvShow(): LiveData<PagedList<TvShow>> {
        return tvShowRepository.getFavTvShow()
    }

    override suspend fun setFavTvShow(tvShow: TvShow) {
        return tvShowRepository.setFavTvShow(tvShow)
    }

    override suspend fun removeFavTvShow(tvShow: TvShow) {
        return tvShowRepository.removeFavTvShow(tvShow)
    }

    override fun isFavorited(id: Int): Flow<Boolean> {
        return tvShowRepository.isFavorited(id)
    }
}