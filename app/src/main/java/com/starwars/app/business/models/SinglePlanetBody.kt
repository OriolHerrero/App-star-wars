package com.starwars.app.business.models

data class SinglePlanetBody(
    val message: String,
    val result: PlanetPropertiesWrapper,
    val apiVersion: Double,
    val timestamp: String,
    val support: Map<String, Any>,
    val social: Map<String, Any>
)

data class PlanetPropertiesWrapper(
    val properties: PlanetProperties,
    val _id: String,
    val description: String,
    val uid: Int,
    val _v: Double
)

data class PlanetProperties(
    val climate: String,
    val created: String,
    val diameter: String,
    val edited: String,
    val films: List<String>,
    val gravity: String,
    val name: String,
    val orbital_period: String,
    val population: String,
    val residents: List<String>,
    val rotation_period: String,
    val surface_water: String,
    val terrain: String,
    val url: String
)