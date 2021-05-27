package com.rasyidin.myfilmlist.ui.feature.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.core.domain.usecase.movies.IMoviesUseCase
import com.rasyidin.myfilmlist.core.domain.usecase.tvshow.ITvShowUseCase
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Mock
    private val tvUseCase = mock<ITvShowUseCase>()

    @Mock
    private val movieUseCase = mock<IMoviesUseCase>()

    @Mock
    private lateinit var observerMovie: Observer<PagedList<Movie>>

    @Mock
    private lateinit var observerTv: Observer<PagedList<TvShow>>

    @Mock
    private lateinit var pagedListMovie: PagedList<Movie>

    @Mock
    private lateinit var pagedListTvShow: PagedList<TvShow>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(movieUseCase, tvUseCase)
    }

    @Test
    fun getFavMovie() {
        val dummyFavMovies = pagedListMovie
        val movies = MutableLiveData<PagedList<Movie>>()
        `when`(dummyFavMovies.size).thenReturn(5)
        movies.value = dummyFavMovies

        `when`(movieUseCase.getFavMovies()).thenReturn(movies)
        val favMovies = viewModel.getFavMovie().value
        verify(movieUseCase).getFavMovies()
        assertNotNull(favMovies)
        assertEquals(5, favMovies?.size)

        viewModel.getFavMovie().observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyFavMovies)
    }

    @Test
    fun getFavTvShow() {
        val dummyFavTvShow = pagedListTvShow
        val listTvShow = MutableLiveData<PagedList<TvShow>>()
        `when`(dummyFavTvShow.size).thenReturn(5)
        listTvShow.value = dummyFavTvShow

        `when`(tvUseCase.getFavTvShow()).thenReturn(listTvShow)
        val favTvShow = viewModel.getFavTvShow().value
        verify(tvUseCase).getFavTvShow()
        assertNotNull(favTvShow)
        assertEquals(5, favTvShow?.size)

        viewModel.getFavTvShow().observeForever(observerTv)
        verify(observerTv).onChanged(dummyFavTvShow)
    }
}