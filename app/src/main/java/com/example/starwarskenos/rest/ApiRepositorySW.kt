package com.example.starwarskenos.rest

import retrofit2.http.GET

interface ApiRepositorySW {
    @GET("people/")
    fun people(): Any
}