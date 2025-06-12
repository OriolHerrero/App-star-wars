package com.starwars.app.business

import com.starwars.app.rest.ApiRepositorySW

interface SWBusiness {
    fun loadPlanets(): Any
}

class SWBusinessImpl(private val apiRepositorySW: ApiRepositorySW): SWBusiness {

    override fun loadPlanets(): Any {
        return apiRepositorySW.planets()
    }
}