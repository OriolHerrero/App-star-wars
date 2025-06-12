package com.starwars.app.rest

import retrofit2.http.GET

interface ApiRepositorySW {

    @GET("planets/")
    fun planets(): Any

    @GET("people/")
    fun people(): Any
}