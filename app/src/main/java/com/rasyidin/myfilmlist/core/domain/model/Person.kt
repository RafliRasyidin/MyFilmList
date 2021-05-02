package com.rasyidin.myfilmlist.core.domain.model

data class Person(
    val alsoKnownAs: List<String>,
    val biography: String? = "",
    val birthday: String? = "",
    val gender: Int = 0,
    val id: Int = 0,
    val imdbId: String? = "",
    val knownForDepartment: String? = "",
    val name: String? = "",
    val placeOfBirth: String? = "",
    val popularity: Double = 0.0,
    val profilePath: String? = "",
    val character: String? = ""
)