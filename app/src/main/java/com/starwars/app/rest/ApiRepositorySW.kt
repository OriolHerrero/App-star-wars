package com.starwars.app.rest

import com.starwars.app.business.models.FilmListBody
import com.starwars.app.business.models.PlanetListBody
import com.starwars.app.business.models.SinglePlanetBody
import com.starwars.app.business.models.SingleVehicleBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiRepositorySW {

    @GET("planets/")
    fun planets(): Call<PlanetListBody>

    @GET
    fun additionalPlanets(@Url url: String): Call<PlanetListBody>

    @GET
    fun planet(@Url url: String): Call<SinglePlanetBody>

    @GET("films/")
    fun films(): Call<FilmListBody>

    @GET
    fun vehicle(@Url url: String): Call<SingleVehicleBody>
}