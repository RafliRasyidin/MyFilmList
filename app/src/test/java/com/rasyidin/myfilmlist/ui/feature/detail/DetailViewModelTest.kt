package com.rasyidin.myfilmlist.ui.feature.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase
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
class DetailViewModelTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel

    @Mock
    private val tvUseCase = mock<ITvShowUseCase>()

    @Mock
    private val movieUseCase = mock<IMoviesUseCase>()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private var movieId: Int? = null
    private var tvShowId: Int? = null

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        val dummyDetailMovie = Resource.Success(DataDummy.generateDummyDetailMovie())
        val movie = flow {
            emit(dummyDetailMovie)
        }

        val dummyDetailTvShow = Resource.Success(DataDummy.generateDummyDetailTv())
        val tvShow = flow {
            emit(dummyDetailTvShow)
        }

        tvShowId = dummyDetailMovie.data?.id
        movieId = dummyDetailMovie.data?.id

        movieId?.let {
            `when`(movieUseCase.getDetail(it)).thenReturn(movie)
        }

        tvShowId?.let {
            `when`(tvUseCase.getDetail(it)).thenReturn(tvShow)
        }

        viewModel = DetailViewModel(movieUseCase, tvUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun getDetailMovie() {
        movieId?.let {
            val movie = viewModel.getDetailMovie(it).getValueOrAwait()
            verify(movieUseCase).getDetail(it)
            assertNotNull(movie.data)
        }


    }

    @Test
    fun getDetailTvShow() {
        tvShowId?.let {
            val tvShow = viewModel.getDetailTvShow(it).getValueOrAwait()
            verify(tvUseCase).getDetail(it)
            assertNotNull(tvShow.data)
        }
    }
}