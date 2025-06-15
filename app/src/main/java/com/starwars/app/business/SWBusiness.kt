package com.starwars.app.business

import com.starwars.app.rest.ApiRepositorySW
import retrofit2.Call
import retrofit2.Response

interface SWBusiness {
    fun loadPlanets(): Any
}

class SWBusinessImpl(private val apiRepositorySW: ApiRepositorySW): SWBusiness {

    override fun loadPlanets(): Map<String, Any> {
        var map: Map<String, Any> = emptyMap()
        apiRepositorySW.planets().enqueue(object: retrofit2.Callback<Map<String, Any>> {
            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                if (response.isSuccessful) {
                    response.body()?.let { map = it }
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {

            }
        })
        return map
    }
}