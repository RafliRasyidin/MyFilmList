package com.rasyidin.myfilmlist.di

import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase
import com.rasyidin.myfilmlist.core.domain.usecase.movies.MoviesInteractors
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.ITvShowUseCase
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.TvShowInteractors
import com.rasyidin.myfilmlist.ui.feature.detail.DetailViewModel
import com.rasyidin.myfilmlist.ui.feature.favorite.FavoriteViewModel
import com.rasyidin.myfilmlist.ui.feature.home.MoviesViewModel
import com.rasyidin.myfilmlist.ui.feature.search.SearchViewModel
import com.rasyidin.myfilmlist.ui.feature.tvshow.TvShowViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMoviesUseCase> { MoviesInteractors(get()) }
    factory<ITvShowUseCase> { TvShowInteractors(get()) }
}

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get(), get()) }
}