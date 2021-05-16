package com.rasyidin.myfilmlist.core.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.data.source.local.MovieLocalDataSource
import com.rasyidin.myfilmlist.core.data.source.local.entity.MovieEntity
import com.rasyidin.myfilmlist.core.data.source.remote.MoviesRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.movies.MovieItemsResponse
import com.rasyidin.myfilmlist.core.utils.toMovieEntity
import com.rasyidin.myfilmlist.utils.DataDummy
import com.rasyidin.myfilmlist.utils.PagedListUtil
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
        moviesRepository = MoviesRepository(remote, local)
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

    @Test
    fun getFavMovies() {
        val dataSource =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
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