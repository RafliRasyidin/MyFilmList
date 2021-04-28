package com.rasyidin.myfilmlist.ui.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase

class MoviesViewModel(useCase: IMoviesUseCase) : ViewModel() {

    val getPopular: LiveData<Resource<List<Movie>>> =
        useCase.getPopular().asLiveData()

    val getTopRated: LiveData<Resource<List<Movie>>> =
        useCase.getTopRated().asLiveData()

    val getNowPlaying: LiveData<Resource<List<Movie>>> =
        useCase.getNowPlaying().asLiveData()

    val getUpComing: LiveData<Resource<List<Movie>>> =
        useCase.getUpComing().asLiveData()

}