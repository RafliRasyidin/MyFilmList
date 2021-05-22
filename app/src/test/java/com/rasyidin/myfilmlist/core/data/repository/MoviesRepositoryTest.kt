package com.rasyidin.myfilmlist.core.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.data.source.local.MovieLocalDataSource
import com.rasyidin.myfilmlist.core.data.source.local.entity.movie.FavMovieEntity
import com.rasyidin.myfilmlist.core.data.source.remote.MoviesRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.utils.toMovieEntity
import com.rasyidin.myfilmlist.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val remote = mock(MoviesRemoteDataSource::class.java)

    @Mock
    private val local = mock(MovieLocalDataSource::class.java)

    private lateinit var moviesRepository: MoviesRepository

    private var movieId: Int = 0

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        movieId = DataDummy.generateDummyDetailMovie().id
        moviesRepository = MoviesRepository(remote, local)
    }

    @Test
    fun getNowPlaying() {
        val dummyMovies = flow {
            emit(DataDummy.generateDummyMovies().toListNowPlayingMovieEntity())
        }
        `when`(local.getAllNowPlayingMovie()).thenReturn(dummyMovies)

        testScope.launch {
            val data = moviesRepository.getNowPlaying().first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }


    @Test
    fun getPopular() {
        val dummyMovies = flow {
            emit(DataDummy.generateDummyMovies().toListPopularMovieEntity())
        }
        `when`(local.getAllPopularMovie()).thenReturn(dummyMovies)

        testScope.launch {
            val data = moviesRepository.getPopular().first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun getTopRated() {
        val dummyMovies = flow {
            emit(DataDummy.generateDummyMovies().toListTopRatedEntity())
        }
        `when`(local.getAllTopRatedMovie()).thenReturn(dummyMovies)

        testScope.launch {
            val data = moviesRepository.getTopRated().first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun getUpComing() {
        val dummyMovies = flow {
            emit(DataDummy.generateDummyMovies().toListUpComingEntity())
        }
        `when`(local.getAllUpComingMovie()).thenReturn(dummyMovies)
        testScope.launch {
            val data = moviesRepository.getUpComing().first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun searchMovies() {
        val query = "Avengers"
        val dummySearch = DataDummy.generateDummySearchMovies().toListMovieItemResponse()
        val dummyResponse = MutableStateFlow(dummySearch)

        testScope.launch {
            `when`(remote.searchMovie(query)).thenReturn(dummyResponse.map {
                ApiResponse.Success(it)
            })
            val data = moviesRepository.searchMovies(query).first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun `Search Result is Not Found`() {
        val query = "kajwhdkawhd"
        val dummySearch = DataDummy.generateDummySearchMovies().toListMovieItemResponse()
        val dummyResponse = MutableStateFlow(dummySearch)

        testScope.launch {
            `when`(remote.searchMovie(query)).thenReturn(dummyResponse.map {
                ApiResponse.Success(it)
            })
            val data = moviesRepository.searchMovies(query).first().data
            assertNull(data)
            assertEquals(0, data?.size)
        }
    }

    @Test
    fun `Error Response`() {
        val movie = flow {
            emit(ApiResponse.Error("Something Wrong!"))
        }
        testScope.launch {
            `when`(remote.getUpcoming()).thenReturn(movie)
            moviesRepository.getUpComing().collect { resource ->
                assertTrue(resource is Resource.Error)
                assertEquals("Something Wrong!", resource.message)
            }
        }
    }

    @Test
    fun getDetail() {
        val detail = flow {
            emit(ApiResponse.Success(DataDummy.generateDummyDetailMovie().toMovieItemsResponse()))
        }
        testScope.launch {
            `when`(remote.getDetail(movieId)).thenReturn(detail)
            val data = moviesRepository.getDetail(movieId).first().data
            assertNotNull(data)
            assertEquals(movieId, data?.id)
        }
    }

    @Test
    fun getCreditsMovie() {
        val credits = flow {
            emit(ApiResponse.Success(DataDummy.generateDummyCredits()))
        }
        testScope.launch {
            `when`(remote.getCreditsMovie(movieId)).thenReturn(credits)
            val data = moviesRepository.getCreditsMovie(movieId).first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun getFavMovies() {
        val dataSource =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavMovieEntity>
        `when`(local.getFavMovies()).thenReturn(dataSource)

        val movies = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        assertNotNull(movies.data)
        assertEquals(3, movies.data?.size)
    }

    @Test
    fun setFavMovie() {
        testScope.launch {
            val favorite = DataDummy.generateDummyDetailMovie()
            doNothing().`when`(local).setFavMovie(favorite.toMovieEntity())
            moviesRepository.setFavMovie(favorite)

            verify(local).setFavMovie(favorite.toMovieEntity())
        }
    }

    @Test
    fun removeFav() {
        testScope.launch {
            val favorite = DataDummy.generateDummyMovies().first()
            doNothing().`when`(local).removeFavMovie(favorite.toMovieEntity())
            moviesRepository.removeFavMovie(favorite)

            verify(local).removeFavMovie(favorite.toMovieEntity())
        }
    }

    @Test
    fun isFavorited() {
        movieId = DataDummy.generateDummyMovies().first().id
        val isFavorited: Flow<Boolean> = flow {
            emit(true)
        }
        `when`(local.isFavorited(movieId)).thenReturn(isFavorited)

        testScope.launch {
            val data = moviesRepository.isFavorited(movieId)
            assertEquals(true, data.first())
        }
    }
}