package com.rasyidin.myfilmlist.ui.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.ITvShowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieUseCase: IMoviesUseCase,
    private val tvShowUseCase: ITvShowUseCase
) : ViewModel() {

    fun getDetailMovie(movieId: Int) = movieUseCase.getDetail(movieId).asLiveData()

    fun getDetailTvShow(tvId: Int) = tvShowUseCase.getDetail(tvId).asLiveData()

    fun getCreditsMovie(movieId: Int) = movieUseCase.getCreditMovie(movieId).asLiveData()

    fun getCreditsTvShow(tvId: Int) = tvShowUseCase.getCreditsTvShow(tvId).asLiveData()

    fun setFav(tvShow: TvShow) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowUseCase.setFavTvShow(tvShow)
        }
    }

    fun setFav(movie: Movie) {
        viewModelScope.launch {
            movieUseCase.setFavMovie(movie)
        }
    }

    fun removeFav(tvShow: TvShow) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowUseCase.removeFavTvShow(tvShow)
        }
    }

    fun removeFav(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.removeFavMovie(movie)
        }
    }

    fun isFavorited(id: Int) = tvShowUseCase.isFavorited(id).asLiveData()
}