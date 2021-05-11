package com.rasyidin.myfilmlist.ui.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase
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
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val moviesUseCase = mock<IMoviesUseCase>()

    private lateinit var viewModel: MoviesViewModel

    @Mock
    private lateinit var observer: Observer<Resource<List<Movie>>>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        val dummyMovies = Resource.Success(DataDummy.generateDummyMovies())
        val movie = flow {
            emit(dummyMovies)
        }
        `when`(moviesUseCase.getPopular()).thenReturn(movie)
        `when`(moviesUseCase.getUpComing()).thenReturn(movie)
        `when`(moviesUseCase.getTopRated()).thenReturn(movie)
        `when`(moviesUseCase.getNowPlaying()).thenReturn(movie)

        viewModel = MoviesViewModel(moviesUseCase)
    }

    @Test
    fun getGetPopular() {
        val listMovies = viewModel.getPopular.getValueOrAwait()
        verify(moviesUseCase).getPopular()
        assertNotNull(listMovies.data)

        viewModel.getPopular.observeForever(observer)
        verify(observer).onChanged(listMovies)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun getGetTopRated() {
        val movies = viewModel.getTopRated.getValueOrAwait()
        verify(moviesUseCase).getTopRated()
        assertNotNull(movies.data)

        viewModel.getTopRated.observeForever(observer)
        verify(observer).onChanged(movies)
    }

    @Test
    fun getGetNowPlaying() {
        val movies = viewModel.getNowPlaying.getValueOrAwait()
        verify(moviesUseCase).getNowPlaying()
        assertNotNull(movies.data)

        viewModel.getNowPlaying.observeForever(observer)
        verify(observer).onChanged(movies)
    }

    @Test
    fun getGetUpComing() {
        val movies = viewModel.getUpComing.getValueOrAwait()
        verify(moviesUseCase).getUpComing()
        assertNotNull(movies.data)

        viewModel.getUpComing.observeForever(observer)
        verify(observer).onChanged(movies)
    }

}