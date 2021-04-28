package com.rasyidin.myfilmlist.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.ITvShowUseCase
import com.rasyidin.myfilmlist.ui.helper.Constants.DEBOUNCE_DURATION
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(movieUseCase: IMoviesUseCase, tvShowUseCase: ITvShowUseCase) : ViewModel() {

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val searchMovies = queryChannel.asFlow()
        .debounce(DEBOUNCE_DURATION)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest { query ->
            movieUseCase.searchMovies(query).asLiveData()
        }
        .asLiveData(viewModelScope.coroutineContext)

    val searchTvShow = queryChannel.asFlow()
        .debounce(DEBOUNCE_DURATION)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest { query ->
            tvShowUseCase.searchTvShow(query).asLiveData()
        }
        .asLiveData(viewModelScope.coroutineContext)

}