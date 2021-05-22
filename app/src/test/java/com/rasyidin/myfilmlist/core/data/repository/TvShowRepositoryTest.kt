package com.rasyidin.myfilmlist.core.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.data.source.local.TvLocalDataSource
import com.rasyidin.myfilmlist.core.data.source.local.entity.tvshow.FavTvShowEntity
import com.rasyidin.myfilmlist.core.data.source.remote.TvShowRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse
import com.rasyidin.myfilmlist.core.utils.toTvShowEntity
import com.rasyidin.myfilmlist.utils.*
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
class TvShowRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val remote = mock(TvShowRemoteDataSource::class.java)

    @Mock
    private val local = mock(TvLocalDataSource::class.java)

    private lateinit var repository: TvShowRepository

    private var tvId: Int = 0

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        tvId = DataDummy.generateDummyDetailTv().id
        repository = TvShowRepository(remote, local)
    }

    @Test
    fun getAiringToday() {
        val dummyTv = flow {
            emit(DataDummy.generateDummyTvShow().toListAiringTodayEntity())
        }
        `when`(local.getAllAiringTodayTvShow()).thenReturn(dummyTv)
        testScope.launch {
            val data = repository.getAiringToday().first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun getOnTheAir() {
        val dummyTv = flow {
            emit(DataDummy.generateDummyTvShow().toListOnTheAirEntity())
        }
        testScope.launch {
            `when`(local.getAllOnTheAirTvShow()).thenReturn(dummyTv)
            val data = repository.getAiringToday().first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun getTopRated() {
        val dummyTv = flow {
            emit(DataDummy.generateDummyTvShow().toListTopRatedTvShowEntity())
        }
        testScope.launch {
            `when`(local.getAllTopRatedTvShow()).thenReturn(dummyTv)
            val data = repository.getTopRated().first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun getPopular() {
        val dummyTv = flow {
            emit(DataDummy.generateDummyTvShow().toListPopularTvShowEntity())
        }
        testScope.launch {
            `when`(local.getAllPopularTvShow()).thenReturn(dummyTv)
            val data = repository.getPopular().first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun searchTvShow() {
        val query = "Naruto"
        val dummySearchTvShow = DataDummy.generateDummySearchTvShow().toListTvItemsResponse()
        val dummyResponse = MutableStateFlow(dummySearchTvShow)

        testScope.launch {
            `when`(remote.searchTvShow(query)).thenReturn(dummyResponse.map { ApiResponse.Success(it) })
            val data = repository.searchTvShow(query).first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun `Search Result is Not Found`() {
        val query = "kajwhdkawhd"
        val dummySearchTvShow = DataDummy.generateDummySearchTvShow().toListTvItemsResponse()
        val dummyResponse = MutableStateFlow(dummySearchTvShow)

        testScope.launch {
            `when`(remote.searchTvShow(query)).thenReturn(dummyResponse.map { ApiResponse.Success(it) })
            val data = repository.searchTvShow(query).first().data
            assertNull(data)
            assertEquals(0, data?.size)
        }
    }

    @Test
    fun `Error Response`() {
        val tvShow = flow {
            emit(ApiResponse.Error("Something Wrong!"))
        }
        testScope.launch {
            `when`(remote.getPopular()).thenReturn(tvShow)
            repository.getPopular().collect { resource ->
                assertTrue(resource is Resource.Error)
                assertEquals("Something Wrong!", resource.message)
            }
        }
    }

    @Test
    fun getDetail() {
        val dummyDetailTvShow = flow {
            emit(ApiResponse.Success(DataDummy.generateDummyDetailTv().toTvItemsResponse()))
        }
        testScope.launch {
            `when`(remote.getDetail(tvId)).thenReturn(dummyDetailTvShow)
            val data = repository.getDetail(tvId).first().data
            assertNotNull(data)
            assertEquals(tvId, data?.id)
        }
    }

    @Test
    fun getCreditsTvShow() {
        val credits = flow {
            emit(ApiResponse.Success(DataDummy.generateDummyCredits()))
        }
        testScope.launch {
            `when`(remote.getCreditsTvShow(tvId)).thenReturn(credits)
            val data = repository.getCreditsTvShow(tvId).first().data
            assertNotNull(data)
            assertEquals(3, data?.size)
        }
    }

    @Test
    fun getFavTvShow() {
        val dataSource =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavTvShowEntity>
        `when`(local.getFavTvShow()).thenReturn(dataSource)

        val tvShow = Resource.Success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        assertNotNull(tvShow.data)
        assertEquals(3, tvShow.data?.size)
    }

    @Test
    fun setFavTvShow() {
        testScope.launch {
            val favorite = DataDummy.generateDummyDetailTv()
            doNothing().`when`(local).setFavTvShow(favorite.toTvShowEntity())
            repository.setFavTvShow(favorite)

            verify(local).setFavTvShow(favorite.toTvShowEntity())
        }
    }

    @Test
    fun removeFavTvShow() {
        testScope.launch {
            val favorite = DataDummy.generateDummyDetailTv()
            doNothing().`when`(local).removeFavTvShow(favorite.toTvShowEntity())
            repository.removeFavTvShow(favorite)

            verify(local).setFavTvShow(favorite.toTvShowEntity())
        }
    }

    @Test
    fun isFavorited() {
        tvId = DataDummy.generateDummyDetailTv().id
        val isFavorited: Flow<Boolean> = flow {
            emit(true)
        }
        `when`(local.isFavorited(tvId)).thenReturn(isFavorited)

        testScope.launch {
            val data = repository.isFavorited(tvId)
            assertEquals(true, data)
        }
    }

    private fun responseHandle(response: BaseResponse<TvItemsResponse>): Flow<ApiResponse<List<TvItemsResponse>>> {
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