package com.rasyidin.myfilmlist.core.domain.usecase.tvshow

import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.Person
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface ITvShowUseCase {

    fun getAiringToday(): Flow<Resource<List<TvShow>>>

    fun getOnTheAir(): Flow<Resource<List<TvShow>>>

    fun getTopRated(): Flow<Resource<List<TvShow>>>

    fun getPopular(): Flow<Resource<List<TvShow>>>

    fun searchTvShow(query: String?): Flow<Resource<List<TvShow>>>

    fun getDetail(tvId: Int): Flow<Resource<TvShow>>

    fun getCreditsTvShow(tvId: Int): Flow<Resource<List<Person>>>
}