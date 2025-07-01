package com.starwars.app.business.models

data class SingleVehicleBody(
    val message: String,
    val result: VehiclePropertiesWrapper,
    val apiVersion: Double,
    val timestamp: String,
    val support: Map<String, Any>,
    val social: Map<String, Any>
)

data class VehiclePropertiesWrapper(
    val properties: VehicleProperties,
    val _id: String,
    val description: String,
    val uid: Int,
    val _v: Double
)

data class VehicleProperties(
    val created: String,
    val edited: String,
    val consumables: String,
    val name: String,
    val cargo_capacity: String,
    val passengers: String,
    val max_atmosphering_speed: String,
    val crew: String,
    val length: String,
    val model: String,
    val cost_in_credits: String,
    val manufacturer: String,
    val vehicle_class: String,
    val pilots: List<String>,
    val films: List<String>,
    val url: String
)
