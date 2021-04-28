package com.rasyidin.myfilmlist.ui.feature.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.ITvShowUseCase
import kotlinx.coroutines.Dispatchers

class TvShowViewModel(private val useCase: ITvShowUseCase): ViewModel() {

    val getAiringToday = useCase.getAiringToday().asLiveData(Dispatchers.IO)

    val getOnTheAir = useCase.getOnTheAir().asLiveData(Dispatchers.IO)

    val getTopRated = useCase.getTopRated().asLiveData(Dispatchers.IO)

    val getPopular = useCase.getPopular().asLiveData(Dispatchers.IO)

}