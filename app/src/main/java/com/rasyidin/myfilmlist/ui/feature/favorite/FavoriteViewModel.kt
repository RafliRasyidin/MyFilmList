package com.rasyidin.myfilmlist.ui.feature.favorite

import androidx.lifecycle.ViewModel
import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.ITvShowUseCase

class FavoriteViewModel(
    private val movieUseCase: IMoviesUseCase,
    private val tvShowUseCase: ITvShowUseCase
) : ViewModel() {

    fun getFavMovie() = movieUseCase.getFavMovies()

    fun getFavTvShow() = tvShowUseCase.getFavTvShow()
}