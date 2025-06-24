package com.starwars.app.business

import com.starwars.app.business.models.PlanetListBody
import com.starwars.app.business.models.SinglePlanetBody
import com.starwars.app.rest.ApiRepositorySW
import retrofit2.Callback

interface SWBusiness {
    fun loadPlanets(): PlanetListBody?
    fun loadPlanet(url: String, callback: Callback<SinglePlanetBody>)
}

class SWBusinessImpl(private val apiRepositorySW: ApiRepositorySW): SWBusiness {

    override fun loadPlanets(): PlanetListBody? {
        val response = apiRepositorySW.planets().execute()
        return response.body()
    }

    override fun loadPlanet(url: String, callback: Callback<SinglePlanetBody>) {
        apiRepositorySW.planet(url).enqueue(callback)
    }
}