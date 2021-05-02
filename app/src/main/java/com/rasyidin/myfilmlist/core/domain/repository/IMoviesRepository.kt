package com.rasyidin.myfilmlist.core.domain.repository

import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    fun getNowPlaying() : Flow<Resource<List<Movie>>>

    fun getPopular(): Flow<Resource<List<Movie>>>

    fun getTopRated(): Flow<Resource<List<Movie>>>

    fun getUpComing(): Flow<Resource<List<Movie>>>

    fun searchMovies(query: String?): Flow<Resource<List<Movie>>>

    fun getDetail(movieId: Int): Flow<Resource<Movie>>

    fun getCreditsMovie(movieId: Int): Flow<Resource<List<Person>>>
}