package com.rasyidin.myfilmlist.ui.feature.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.ITvShowUseCase
import com.rasyidin.myfilmlist.utils.DataDummy
import com.rasyidin.myfilmlist.utils.getValueOrAwait
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TvShowViewModel

    @Mock
    private val useCase = mock<ITvShowUseCase>()

    @Mock
    private lateinit var observer: Observer<Resource<List<TvShow>>>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        val dummyTvShow = Resource.Success(DataDummy.generateDummyTvShow())
        val tvShow = flow {
            emit(dummyTvShow)
        }

        `when`(useCase.getAiringToday()).thenReturn(tvShow)
        `when`(useCase.getOnTheAir()).thenReturn(tvShow)
        `when`(useCase.getPopular()).thenReturn(tvShow)
        `when`(useCase.getTopRated()).thenReturn(tvShow)

        viewModel = TvShowViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun getGetAiringToday() {
        val listTvShow = viewModel.getAiringToday.getValueOrAwait()
        verify(useCase).getAiringToday()
        assertNotNull(listTvShow.data)

        viewModel.getAiringToday.observeForever(observer)
        verify(observer).onChanged(listTvShow)
    }

    @Test
    fun getGetOnTheAir() {
        val listTvShow = viewModel.getOnTheAir.getValueOrAwait()
        verify(useCase).getOnTheAir()
        assertNotNull(listTvShow.data)

        viewModel.getOnTheAir.observeForever(observer)
        verify(observer).onChanged(listTvShow)
    }

    @Test
    fun getGetTopRated() {
        val listTvShow = viewModel.getTopRated.getValueOrAwait()
        verify(useCase).getTopRated()
        assertNotNull(listTvShow.data)

        viewModel.getTopRated.observeForever(observer)
        verify(observer).onChanged(listTvShow)
    }

    @Test
    fun getGetPopular() {
        val listTvShow = viewModel.getPopular.getValueOrAwait()
        verify(useCase).getPopular()
        assertNotNull(listTvShow.data)

        viewModel.getPopular.observeForever(observer)
        verify(observer).onChanged(listTvShow)
    }
}