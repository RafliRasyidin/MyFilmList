package com.rasyidin.myfilmlist.core.utils

import com.rasyidin.myfilmlist.core.data.source.remote.response.movies.MovieItemsResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.TvShow

fun MovieItemsResponse.toMovie() = Movie(
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate,
    genres = this.genres,
    id = this.id,
    title = this.title,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = this.posterPath,
    voteCount = this.voteCount,
    voteAverage = this.voteAverage,
    runtime = this.runtime
)

fun TvItemsResponse.toTvShow() = TvShow(
    backdropPath = this.backdropPath,
    firstAirDate = this.firstAirDate,
    genres = this.genres,
    id = this.id,
    name = this.name,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = this.posterPath,
    voteCount = this.voteCount,
    voteAverage = this.voteAverage
)

fun List<MovieItemsResponse>.toListMovie(): List<Movie> {
    val movies = ArrayList<Movie>()
    this.map {
        val movie = Movie(
            backdropPath = it.backdropPath,
            releaseDate = it.releaseDate,
            genres = emptyList(),
            id = it.id,
            title = it.title,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            voteCount = it.voteCount,
            voteAverage = it.voteAverage,
            runtime = it.runtime
        )
        movies.add(movie)
    }
    return movies
}

fun List<TvItemsResponse>.toListTvShow(): List<TvShow> {
    val listTvShow = ArrayList<TvShow>()
    this.map {
        val tvShow = TvShow(
            backdropPath = it.backdropPath,
            firstAirDate = it.firstAirDate,
            genres = emptyList(),
            id = it.id,
            name = it.name,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            voteCount = it.voteCount,
            voteAverage = it.voteAverage
        )
        listTvShow.add(tvShow)
    }
    return listTvShow
}
