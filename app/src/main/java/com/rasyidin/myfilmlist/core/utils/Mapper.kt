package com.rasyidin.myfilmlist.core.utils

import com.rasyidin.myfilmlist.core.data.source.local.entity.movie.*
import com.rasyidin.myfilmlist.core.data.source.local.entity.tvshow.*
import com.rasyidin.myfilmlist.core.data.source.remote.response.CastResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.movies.MovieItemsResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.Person
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

fun FavMovieEntity.toMovie() = Movie(
    id = this.id,
    title = this.title,
    posterPath = this.posterPath,
    voteAverage = this.voteAverage,
    genres = emptyList()
)

fun FavTvShowEntity.toTvShow() = TvShow(
    id = this.id,
    name = this.name,
    posterPath = this.posterPath,
    voteAverage = this.voteAverage,
    genres = emptyList()
)

fun TvShow.toTvShowEntity() = FavTvShowEntity(
    id = this.id,
    name = this.name,
    posterPath = this.posterPath,
    voteAverage = this.voteAverage
)

fun Movie.toMovieEntity() = FavMovieEntity(
    id = this.id,
    title = this.title,
    posterPath = this.posterPath,
    voteAverage = this.voteAverage
)

@JvmName("toTopRatedTvShowEntity")
fun List<TvItemsResponse>.toTopRatedEntity(): List<TopRatedTvShowEntity> {
    if (this.isNullOrEmpty()) return emptyList()
    val tvShow = ArrayList<TopRatedTvShowEntity>()
    this.map {
        val entity = TopRatedTvShowEntity(
            id = it.id,
            name = it.name,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
        tvShow.add(entity)
    }

    return tvShow
}

fun List<TvItemsResponse>.toOnTheAirEntity(): List<OnTheAirTvShowEntity> {
    if (this.isNullOrEmpty()) return emptyList()
    val tvShow = ArrayList<OnTheAirTvShowEntity>()
    this.map {
        val entity = OnTheAirTvShowEntity(
            id = it.id,
            name = it.name,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
        tvShow.add(entity)
    }

    return tvShow
}

@JvmName("toPopularTvShowEntity")
fun List<TvItemsResponse>.toPopularEntity(): List<PopularTvShowEntity> {
    if (this.isNullOrEmpty()) return emptyList()
    val tvShow = ArrayList<PopularTvShowEntity>()
    this.map {
        val entity = PopularTvShowEntity(
            id = it.id,
            name = it.name,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
        tvShow.add(entity)
    }

    return tvShow
}

fun List<TvItemsResponse>.toAiringTodayEntity(): List<AiringTodayTvShowEntity> {
    if (this.isNullOrEmpty()) return emptyList()
    val tvShow = ArrayList<AiringTodayTvShowEntity>()
    this.map {
        val entity = AiringTodayTvShowEntity(
            id = it.id,
            name = it.name,
            backdropPath = it.backdropPath,
            voteAverage = it.voteAverage
        )
        tvShow.add(entity)
    }

    return tvShow
}

fun List<MovieItemsResponse>.toNowPlayingEntity(): List<NowPlayingMovieEntity> {
    if (this.isNullOrEmpty()) return emptyList()
    val movies = ArrayList<NowPlayingMovieEntity>()
    this.map {
        val entity = NowPlayingMovieEntity(
            id = it.id,
            title = it.title,
            backdropPath = it.backdropPath,
            voteAverage = it.voteAverage
        )
        movies.add(entity)
    }

    return movies
}

fun List<MovieItemsResponse>.toPopularEntity(): List<PopularMovieEntity> {
    if (this.isNullOrEmpty()) return emptyList()
    val movies = ArrayList<PopularMovieEntity>()
    this.map {
        val entity = PopularMovieEntity(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
        movies.add(entity)
    }

    return movies
}

fun List<MovieItemsResponse>.toUpComingEntity(): List<UpComingMovieEntity> {
    if (this.isNullOrEmpty()) return emptyList()
    val movies = ArrayList<UpComingMovieEntity>()
    this.map {
        val entity = UpComingMovieEntity(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
        movies.add(entity)
    }

    return movies
}

fun List<MovieItemsResponse>.toTopRatedEntity(): List<TopRatedMovieEntity> {
    if (this.isNullOrEmpty()) return emptyList()
    val movies = ArrayList<TopRatedMovieEntity>()
    this.map {
        val entity = TopRatedMovieEntity(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
        movies.add(entity)
    }

    return movies
}

@JvmName("toListTvShowAiringToday")
fun List<AiringTodayTvShowEntity>.toListTvShow(): List<TvShow> {
    val listTvShow = ArrayList<TvShow>()
    this.map {
        val tvShow = TvShow(
            backdropPath = it.backdropPath,
            id = it.id,
            name = it.name,
            voteAverage = it.voteAverage,
            genres = emptyList()
        )
        listTvShow.add(tvShow)
    }
    return listTvShow
}

@JvmName("PopularEntityToListTvShow")
fun List<PopularTvShowEntity>.toListTvShow(): List<TvShow> {
    val listTvShow = ArrayList<TvShow>()
    this.map {
        val tvShow = TvShow(
            posterPath = it.posterPath,
            id = it.id,
            name = it.name,
            voteAverage = it.voteAverage,
            genres = emptyList()
        )
        listTvShow.add(tvShow)
    }
    return listTvShow
}

@JvmName("TopRatedEntityToListTvShow")
fun List<TopRatedTvShowEntity>.toListTvShow(): List<TvShow> {
    val listTvShow = ArrayList<TvShow>()
    this.map {
        val tvShow = TvShow(
            posterPath = it.posterPath,
            id = it.id,
            name = it.name,
            voteAverage = it.voteAverage,
            genres = emptyList()
        )
        listTvShow.add(tvShow)
    }
    return listTvShow
}

@JvmName("OnTheAirEntityToListTvShow")
fun List<OnTheAirTvShowEntity>.toListTvShow(): List<TvShow> {
    val listTvShow = ArrayList<TvShow>()
    this.map {
        val tvShow = TvShow(
            posterPath = it.posterPath,
            id = it.id,
            name = it.name,
            voteAverage = it.voteAverage,
            genres = emptyList()
        )
        listTvShow.add(tvShow)
    }
    return listTvShow
}

@JvmName("NowPlayingMovieEntityToListMovie")
fun List<NowPlayingMovieEntity>.toListMovie(): List<Movie> {
    val movies = ArrayList<Movie>()
    this.map {
        val movie = Movie(
            backdropPath = it.backdropPath,
            id = it.id,
            title = it.title,
            voteAverage = it.voteAverage,
            genres = emptyList()
        )
        movies.add(movie)
    }
    return movies
}

@JvmName("PopularEntityToListMovie")
fun List<PopularMovieEntity>.toListMovie(): List<Movie> {
    val movies = ArrayList<Movie>()
    this.map {
        val movie = Movie(
            posterPath = it.posterPath,
            id = it.id,
            title = it.title,
            voteAverage = it.voteAverage,
            genres = emptyList()
        )
        movies.add(movie)
    }
    return movies
}

@JvmName("UpComingEntityToListMovie")
fun List<UpComingMovieEntity>.toListMovie(): List<Movie> {
    val movies = ArrayList<Movie>()
    this.map {
        val movie = Movie(
            posterPath = it.posterPath,
            id = it.id,
            title = it.title,
            voteAverage = it.voteAverage,
            genres = emptyList()
        )
        movies.add(movie)
    }
    return movies
}

@JvmName("TopRatedEntityToListMovie")
fun List<TopRatedMovieEntity>.toListMovie(): List<Movie> {
    val movies = ArrayList<Movie>()
    this.map {
        val movie = Movie(
            posterPath = it.posterPath,
            id = it.id,
            title = it.title,
            voteAverage = it.voteAverage,
            genres = emptyList()
        )
        movies.add(movie)
    }
    return movies
}

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

fun List<CastResponse>.toPerson(): List<Person> {
    val listPerson = ArrayList<Person>()
    this.map {
        val person = Person(
            alsoKnownAs = emptyList(),
            id = it.id,
            name = it.name,
            character = it.character,
            profilePath = it.profilePath,
            popularity = it.popularity
        )
        listPerson.add(person)
    }
    return listPerson
}
