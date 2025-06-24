package com.starwars.app.business.models

data class PlanetListBody(
    val message: String,
    val totalRecords: Double,
    val totalPages: Double,
    val previous: String,
    val next: String,
    val results: MutableList<PlanetHeader>,
    val apiVersion: Double,
    val timestamp: String,
    val support: Map<String, Any>,
    val social: Map<String, Any>)

data class PlanetHeader(val uid: Int, val name: String, val url: String)