package com.rasyidin.myfilmlist.ui.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.ITvShowUseCase

class DetailViewModel(
    private val movieUseCase: IMoviesUseCase,
    private val tvShowUseCase: ITvShowUseCase
) : ViewModel() {

    fun getDetailMovie(movieId: Int) = movieUseCase.getDetail(movieId).asLiveData()

    fun getDetailTvShow(tvId: Int) = tvShowUseCase.getDetail(tvId).asLiveData()

    fun getCreditsMovie(movieId: Int) = movieUseCase.getCreditMovie(movieId).asLiveData()

    fun getCreditsTvShow(tvId: Int) = tvShowUseCase.getCreditsTvShow(tvId).asLiveData()
}