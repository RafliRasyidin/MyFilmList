package com.rasyidin.myfilmlist.core.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.data.source.remote.MoviesRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.movies.MovieItemsResponse
import com.rasyidin.myfilmlist.utils.DataDummy
import com.rasyidin.myfilmlist.utils.toListMovieItemResponse
import com.rasyidin.myfilmlist.utils.toMovieItemsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val remote = Mockito.mock(MoviesRemoteDataSource::class.java)

    private lateinit var moviesRepository: FakeMoviesRepository

    private var movieId: Int = 0

    private lateinit var dummyMovies: BaseResponse<MovieItemsResponse>

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        dummyMovies = BaseResponse(
            data = DataDummy.generateDummyMovies().toListMovieItemResponse(),
            page = 1
        )
        movieId = DataDummy.generateDummyDetailMovie().id
        moviesRepository = FakeMoviesRepository(remote)
    }

    @Test
    fun getNowPlaying() {
        testScope.launch {
            `when`(remote.getNowPlaying()).thenReturn(responseHandle(dummyMovies))
            moviesRepository.getNowPlaying().collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }

    }


    @Test
    fun getPopular() {
        testScope.launch {
            `when`(remote.getPopular()).thenReturn(responseHandle(dummyMovies))
            moviesRepository.getPopular().collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun getTopRated() {
        testScope.launch {
            `when`(remote.getTopRated()).thenReturn(responseHandle(dummyMovies))
            moviesRepository.getTopRated().collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun getUpComing() {
        testScope.launch {
            `when`(remote.getUpcoming()).thenReturn(responseHandle(dummyMovies))
            moviesRepository.getUpComing().collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun searchMovies() {
        val query = "Avengers"
        val dummySearchMovies = BaseResponse(
            data = DataDummy.generateDummySearchMovies().toListMovieItemResponse(),
            page = 1
        )
        testScope.launch {
            `when`(remote.searchMovie(query)).thenReturn(responseHandle(dummySearchMovies))
            moviesRepository.searchMovies(query).collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun `Search Result is Not Found`() {
        val query = "kajwhdkawhd"
        val dummySearchMovies = BaseResponse(
            1,
            emptyList<MovieItemsResponse>()
        )
        testScope.launch {
            `when`(remote.searchMovie(query)).thenReturn(responseHandle(dummySearchMovies))
            moviesRepository.searchMovies(query).collect { resource ->
                if (resource is Resource.Success) {
                    assertNull(resource.data)
                }
            }
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
                if (resource is Resource.Error) {
                    assertEquals("Something Wrong!", resource.message)
                    assertNull(resource.data)
                }
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
            moviesRepository.getDetail(movieId).collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                    assertEquals(movieId, resource.data?.id)
                }
            }
        }
    }

    @Test
    fun getCreditsMovie() {
        val credits = flow {
            emit(ApiResponse.Success(DataDummy.generateDummyCredits()))
        }
        testScope.launch {
            `when`(remote.getCreditsMovie(movieId)).thenReturn(credits)
            moviesRepository.getCreditsMovie(movieId).collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    private fun responseHandle(response: BaseResponse<MovieItemsResponse>): Flow<ApiResponse<List<MovieItemsResponse>>> {
        return flow {
            val data = response.data
            if (data.isNotEmpty()) {
                emit(ApiResponse.Success(data))
            } else {
                emit(ApiResponse.Empty)
            }
        }.catch { e ->
            emit(ApiResponse.Error(e.message.toString()))
        }.flowOn(Dispatchers.IO)
    }
}