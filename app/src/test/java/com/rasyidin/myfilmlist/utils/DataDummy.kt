package com.rasyidin.myfilmlist.utils

import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.TvShow

object DataDummy {

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
}