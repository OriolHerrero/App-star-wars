package com.starwars.app.business.models

data class SingleFilmBody(
    val properties: FilmProperties,
    val _id: String,
    val description: String,
    val uid: Int,
    val _v: Double
)

data class FilmProperties(
    val created: String,
    val edited: String,
    val starships: MutableList<String>,
    val vehicles: MutableList<String>,
    val planets: MutableList<String>,
    val producer: String,
    val title: String,
    val episode_id: Double,
    val director: String,
    val release_date: String,
    val opening_crawl: String,
    val characters: MutableList<String>,
    val species: MutableList<String>,
    val url: String
)