package com.starwars.app.rest

import retrofit2.Call
import retrofit2.http.GET

interface ApiRepositorySW {

    @GET("planets/")
    fun planets(): Call<Map<String, Any>>

    @GET("vehicles/")
    fun vehicles(): Call<Map<String, Any>>
}