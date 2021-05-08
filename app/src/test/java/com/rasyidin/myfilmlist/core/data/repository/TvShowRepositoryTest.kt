package com.rasyidin.myfilmlist.core.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.data.source.remote.TvShowRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.ApiResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.BaseResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse
import com.rasyidin.myfilmlist.utils.DataDummy
import com.rasyidin.myfilmlist.utils.toListTvItemsResponse
import com.rasyidin.myfilmlist.utils.toTvItemsResponse
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

    private lateinit var repository: TvShowRepository

    private var tvId: Int = 0

    private lateinit var dummyTvShow: BaseResponse<TvItemsResponse>

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        dummyTvShow = BaseResponse(
            1,
            DataDummy.generateDummyTvShow().toListTvItemsResponse()
        )
        tvId = DataDummy.generateDummyDetailTv().id
        repository = TvShowRepository(remote)
    }

    @Test
    fun getAiringToday() {
        testScope.launch {
            `when`(remote.getAiringToday()).thenReturn(responseHandle(dummyTvShow))
            repository.getAiringToday().collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun getOnTheAir() {
        testScope.launch {
            `when`(remote.getOnTheAir()).thenReturn(responseHandle(dummyTvShow))
            repository.getOnTheAir().collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun getTopRated() {
        testScope.launch {
            `when`(remote.getTopRated()).thenReturn(responseHandle(dummyTvShow))
            repository.getTopRated().collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun getPopular() {
        testScope.launch {
            `when`(remote.getPopular()).thenReturn(responseHandle(dummyTvShow))
            repository.getPopular().collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun searchTvShow() {
        val query = "Naruto"
        val dummySearchTvShow = BaseResponse(
            1,
            DataDummy.generateDummySearchTvShow().toListTvItemsResponse()
        )

        testScope.launch {
            `when`(remote.searchTvShow(query)).thenReturn(responseHandle(dummySearchTvShow))
            repository.searchTvShow(query).collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun `Search Result is Not Found`() {
        val query = "kajwhdkawhd"
        val dummySearchTvShow = BaseResponse(
            1,
            emptyList<TvItemsResponse>()
        )
        testScope.launch {
            `when`(remote.searchTvShow(query)).thenReturn(responseHandle(dummySearchTvShow))
            repository.searchTvShow(query).collect { resource ->
                if (resource is Resource.Success) {
                    assertNull(resource.data)
                }
            }
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
                if (resource is Resource.Error) {
                    assertEquals("Something Wrong!", resource.message)
                    assertNull(resource.data)
                }
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
            repository.getDetail(tvId).collect { resource ->
                if (resource is Resource.Success) {
                    assertEquals(tvId, resource.data?.id)
                    assertNotNull(resource.data)
                }
            }
        }
    }

    @Test
    fun getCreditsTvShow() {
        val credits = flow {
            emit(ApiResponse.Success(DataDummy.generateDummyCredits()))
        }
        testScope.launch {
            `when`(remote.getCreditsTvShow(tvId)).thenReturn(credits)
            repository.getCreditsTvShow(tvId).collect { resource ->
                if (resource is Resource.Success) {
                    assertNotNull(resource.data)
                }
            }
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