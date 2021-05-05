package com.rasyidin.myfilmlist.utils

import com.rasyidin.myfilmlist.core.data.source.remote.response.CastResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.movies.MovieItemsResponse
import com.rasyidin.myfilmlist.core.data.source.remote.response.tv.TvItemsResponse
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.TvShow

object DataDummy {

    fun generateDummySearchMovies(): List<Movie> {
        val movies = ArrayList<Movie>()

        movies.add(
            Movie(
                "/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
                "2019-04-24",
                emptyList(),
                299534,
                "Avengers: Endgame",
                "After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
                293.211,
                "/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg",
                18019,
                0,
                8.3
            )
        )
        movies.add(
            Movie(
                "/lmZFxXgJE3vgrciwuDib0N8CfQo.jpg",
                "2018-04-25",
                emptyList(),
                299536,
                "Avengers: Infinity War",
                "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
                359.008,
                "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                21688,
                0,
                8.3
            )
        )
        movies.add(
            Movie(
                "/nNmJRkg8wWnRmzQDe2FwKbPIsJV.jpg",
                "2012-04-25",
                emptyList(),
                24428,
                "The Avengers",
                "When an unexpected enemy emerges and threatens global safety and security, Nick Fury, director of the international peacekeeping agency known as S.H.I.E.L.D., finds himself in need of a team to pull the world back from the brink of disaster. Spanning the globe, a daring recruitment effort begins!",
                29.737342,
                "/RYMX2wcKCBAr24UyPD7xwmjaTn.jpg",
                24597,
                0,
                7.7
            )
        )

        return movies
    }

    fun generateDummySearchTvShow(): List<TvShow> {
        val listTvShow = ArrayList<TvShow>()

        listTvShow.add(
            TvShow(
                "/3XlKckxPEa4lg5w4vHnyE35PUyI.jpg",
                "2017-04-05",
                emptyList(),
                70881,
                "Boruto: Naruto Next Generations",
                "The Hidden Leaf Village has entered an era of peace and modernity. Tall buildings line the streets, giant screens flash with images, and the Thunder Rail runs through the village, connecting each district together. Though it's still a ninja village, the number of civilians has increased and the life of the shinobi is beginning to change. Boruto Uzumaki, son of Seventh Hokage Naruto Uzumaki, has enrolled in the Ninja Academy to learn the ways of the ninja. The other students are ready to dismiss him as \"just the son of the Hokage,” but Boruto’s heart and character blow all their assumptions away. As a series of mysterious events begins to unfold, it’s up to Boruto and his new friends to handle them. Like a gale-force wind, Boruto makes his own way into everyone's hearts; his story is about to begin!!",
                36.22,
                "/e0B6i48kxdRkMcK4tR4YNfXGWOc.jpg",
                1521,
                8.0
            )
        )
        listTvShow.add(
            TvShow(
                "/hAE4t1A9Mhrc7ZzbbV06yaYGyQV.jpg",
                "2012-04-03",
                emptyList(),
                43168,
                "NARUTO Spin-Off: Rock Lee & His Ninja Pals",
                "Welcome to the Hidden Leaf Village. The village where Uzumaki Naruto, star of the TV show \"Naruto\" makes his home. Every day, countless powerful ninjas carry out missions and train to hone their skills. Our main character is one of these powerful ninjas...but it's not Naruto! It's the ninja who can't use ninjutsu, Rock Lee!",
                57.873,
                "/1ZuRLgQGPG6K5fcORJQpsmIurZv.jpg",
                15,
                7.1
            )
        )
        listTvShow.add(
            TvShow(
                "/oycArCLGgtWyUz5aho7ojFZkgjN.jpg",
                "2002-10-03",
                emptyList(),
                46260,
                "Naruto",
                "In another world, ninja are the ultimate power, and in the Village Hidden in the Leaves live the stealthiest ninja in the land. Twelve years earlier, the fearsome Nine-Tailed Fox terrorized the village and claimed many lives before it was subdued and its spirit sealed within the body of a baby boy. That boy, Naruto Uzumaki, has grown up to become a ninja-in-training who's more interested in pranks than in studying ninjutsu.. but Naruto is determined to become the greatest ninja ever!",
                347.323,
                "/vauCEnR7CiyBDzRCeElKkCaXIYu.jpg",
                3513,
                8.4
            )
        )
        return listTvShow
    }

    fun generateDummyDetailMovie(): Movie {
        return Movie(
            "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
            "2016-08-03",
            emptyList(),
            297761,
            "Suicide Squad",
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            48.261451,
            "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
            1466,
            0,
            5.91
        )
    }

    fun generateDummyDetailTv(): TvShow {
        return TvShow(
            "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
            "2010-06-08",
            emptyList(),
            31917,
            "Pretty Little Liars",
            "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
            47.432451,
            "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
            133,
            5.04
        )
    }

    fun generateDummyMovies(): List<Movie> {
        val movies = ArrayList<Movie>()

        movies.add(
            Movie(
                "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
                "2016-08-03",
                emptyList(),
                297761,
                "Suicide Squad",
                "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                48.261451,
                "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
                1466,
                0,
                5.91
            )
        )
        movies.add(
            Movie(
                "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                "2016-07-27",
                emptyList(),
                324668,
                "Jason Bourne",
                "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
                30.690177,
                "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
                649,
                0,
                5.25
            )
        )
        movies.add(
            Movie(
                "/zrAO2OOa6s6dQMQ7zsUbDyIBrAP.jpg",
                "2016-06-02",
                emptyList(),
                291805,
                "Now You See Me 2",
                "One year after outwitting the FBI and winning the public’s adulation with their mind-bending spectacles, the Four Horsemen resurface only to find themselves face to face with a new enemy who enlists them to pull off their most dangerous heist yet.",
                29.737342,
                "/hU0E130tsGdsYa4K9lc3Xrn5Wyt.jpg",
                684,
                0,
                6.64
            )
        )

        return movies
    }

    fun generateDummyTvShow(): List<TvShow> {
        val listTvShow = ArrayList<TvShow>()

        listTvShow.add(
            TvShow(
                "/rQGBjWNveVeF8f2PGRtS85w9o9r.jpg",
                "2010-06-08",
                emptyList(),
                31917,
                "Pretty Little Liars",
                "Based on the Pretty Little Liars series of young adult novels by Sara Shepard, the series follows the lives of four girls — Spencer, Hanna, Aria, and Emily — whose clique falls apart after the disappearance of their queen bee, Alison. One year later, they begin receiving messages from someone using the name \"A\" who threatens to expose their secrets — including long-hidden ones they thought only Alison knew.",
                47.432451,
                "/vC324sdfcS313vh9QXwijLIHPJp.jpg",
                133,
                5.04
            )
        )
        listTvShow.add(
            TvShow(
                "/v8Y9yurHuI7MujWQMd8iL3Gy4B5.jpg",
                "2015-05-27",
                emptyList(),
                62560,
                "Mr. Robot",
                "A contemporary and culturally resonant drama about a young programmer, Elliot, who suffers from a debilitating anti-social disorder and decides that he can only connect to people by hacking them. He wields his skills as a weapon to protect the people that he cares about. Elliot will find himself in the intersection between a cybersecurity firm he works for and the underworld organizations that are recruiting him to bring down corporate America.",
                37.882356,
                "/esN3gWb1P091xExLddD2nh4zmi3.jpg",
                649,
                7.5
            )
        )
        listTvShow.add(
            TvShow(
                "/8SAQqivlp74MZ7u55ccR1xa0Nby.jpg",
                "2011-06-23",
                emptyList(),
                37680,
                "Suits",
                "While running from a drug deal gone bad, Mike Ross, a brilliant young college-dropout, slips into a job interview with one of New York City's best legal closers, Harvey Specter. Tired of cookie-cutter law school grads, Harvey takes a gamble by hiring Mike on the spot after he recognizes his raw talent and photographic memory. Mike and Harvey are a winning team. Even though Mike is a genius, he still has a lot to learn about law. And while Harvey may seem like an emotionless, cold-blooded shark, Mike's sympathy and concern for their cases and clients will help remind Harvey why he went into law in the first place. Mike's other allies in the office include the firm's best paralegal Rachel and Harvey's no-nonsense assistant Donna to help him serve justice. Proving to be an irrepressible duo and invaluable to the practice, Mike and Harvey must keep their secret from everyone including managing partner Jessica and Harvey's arch nemesis Louis, who seems intent on making Mike's life as difficult as possible.",
                34.376914,
                "/i6Iu6pTzfL6iRWhXuYkNs8cPdJF.jpg",
                161,
                6.94
            )
        )

        return listTvShow
    }

    fun generateDummyCredits(): List<CastResponse> {
        val cast = ArrayList<CastResponse>()
        cast.add(
            CastResponse(
                character = "The Narrator",
                id = 819,
                name = "Edward Norton",
                popularity = 7.861,
                profilePath = "/5XBzD5WuTyVQZeS4VI25z2moMeY.jpg"
            )
        )
        cast.add(
            CastResponse(
                character = "Tyler Durden",
                id = 287,
                name = "Brad Pitt",
                popularity = 20.431,
                profilePath = "/cckcYc2v0yh1tc9QjRelptcOBko.jpg"
            )
        )
        cast.add(
            CastResponse(
                character = "Marla Singer",
                id = 1283,
                name = "Helena Bonham Carter",
                popularity = 14.399,
                profilePath = "/mW1NolxQmPE16Zg6zuWyZlFgOwL.jpg"
            )
        )

        return cast
    }
}

fun List<Movie>.toListMovieItemResponse(): List<MovieItemsResponse> {
    val movies = ArrayList<MovieItemsResponse>()
    this.map {
        val movie = MovieItemsResponse(
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

fun List<TvShow>.toListTvItemsResponse(): List<TvItemsResponse> {
    val listTvShow = ArrayList<TvItemsResponse>()
    this.map {
        val tvShow = TvItemsResponse(
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

fun Movie.toMovieItemsResponse() = MovieItemsResponse(
    this.backdropPath,
    this.genres,
    this.id,
    this.overview,
    this.popularity,
    this.posterPath,
    this.releaseDate,
    this.title,
    this.voteCount,
    this.runtime,
    this.voteAverage
)

fun TvShow.toTvItemsResponse() = TvItemsResponse(
    this.backdropPath,
    this.firstAirDate,
    this.genres,
    this.id,
    this.name,
    this.overview,
    this.popularity,
    this.posterPath,
    this.voteCount,
    this.voteAverage
)