package com.starwars.app.business.models

data class FilmListBody(
    val message: String,
    val result: MutableList<SingleFilmBody>,
    val apiVersion: Double,
    val timestamp: String,
    val support: Map<String, Any>,
    val social: Map<String, Any>)