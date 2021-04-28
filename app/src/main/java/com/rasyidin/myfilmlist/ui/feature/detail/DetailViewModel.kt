package com.rasyidin.myfilmlist.ui.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.ITvShowUseCase
import kotlinx.coroutines.Dispatchers

class DetailViewModel(
    private val movieUseCase: IMoviesUseCase,
    private val tvShowUseCase: ITvShowUseCase
) : ViewModel() {

    fun getDetailMovie(movieId: Int) = movieUseCase.getDetail(movieId).asLiveData(Dispatchers.IO)

    fun getDetailTvShow(tvId: Int) = tvShowUseCase.getDetail(tvId).asLiveData(Dispatchers.IO)
}