package com.starwars.app.business

import com.starwars.app.business.models.FilmListBody
import com.starwars.app.business.models.PlanetListBody
import com.starwars.app.business.models.SinglePlanetBody
import com.starwars.app.business.models.SingleVehicleBody
import com.starwars.app.rest.ApiRepositorySW
import retrofit2.Callback

interface SWBusiness {
    fun loadPlanets(): PlanetListBody?
    fun loadAdditionalPlanets(url: String): PlanetListBody?
    fun loadPlanet(url: String, callback: Callback<SinglePlanetBody>)
    fun loadFilms(): FilmListBody?
    fun loadVehicle(url: String, callback: Callback<SingleVehicleBody>)
}

class SWBusinessImpl(private val apiRepositorySW: ApiRepositorySW): SWBusiness {

    override fun loadPlanets(): PlanetListBody? {
        val response = apiRepositorySW.planets().execute()
        return response.body()
    }

    override fun loadAdditionalPlanets(url: String): PlanetListBody? {
        val response = apiRepositorySW.additionalPlanets(url).execute()
        return response.body()
    }

    override fun loadPlanet(url: String, callback: Callback<SinglePlanetBody>) {
        apiRepositorySW.planet(url).enqueue(callback)
    }

    override fun loadFilms(): FilmListBody? {
        val response = apiRepositorySW.films().execute()
        return response.body()
    }

    override fun loadVehicle(url: String, callback: Callback<SingleVehicleBody>) {
        apiRepositorySW.vehicle(url).enqueue(callback)
    }
}