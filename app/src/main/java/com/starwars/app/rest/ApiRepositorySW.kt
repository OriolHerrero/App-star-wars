package com.starwars.app.rest

import com.starwars.app.business.PlanetBody
import retrofit2.Call
import retrofit2.http.GET

interface ApiRepositorySW {

    @GET("planets/")
    fun planets(): Call<PlanetBody>

    @GET("vehicles/")
    fun vehicles(): Call<Map<String, Any>>
}