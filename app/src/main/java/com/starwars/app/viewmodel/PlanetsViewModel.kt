package com.starwars.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.starwars.app.business.SWBusiness
import com.starwars.app.business.models.SinglePlanetBody
import com.starwars.app.utils.StorageUtils
import com.starwars.app.views.model.Planet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PlanetsViewModel @Inject constructor(private val swBusiness: SWBusiness, private val application: Application): AndroidViewModel(application) {

    private val PLANETS_LIST = "planetsList"

    var planets: MutableList<Planet> = mutableListOf()
    private val _planetsUpdated = MutableLiveData<MutableList<Planet>>()
    val planetsUpdated: LiveData<MutableList<Planet>> get() = _planetsUpdated


    fun loadData() {
        planets = restorePlanets()
        viewModelScope.launch(Dispatchers.IO) {
            val body = swBusiness.loadPlanets()
            body?.let {
                for (element in it.results) {
                    swBusiness.loadPlanet(element.url, object: Callback<SinglePlanetBody> {
                        override fun onResponse(call: Call<SinglePlanetBody>, response: Response<SinglePlanetBody>) {
                            response.body()?.result?.let { result ->
                                planets.add(Planet(result.uid, result.properties.name, result.properties.population, result.properties.diameter))
                                planets.sortBy { element-> element.id }
                                _planetsUpdated.postValue(planets)
                            }
                        }

                        override fun onFailure(call: Call<SinglePlanetBody>, t: Throwable) {
                            val a = ""
                        }
                    })
                }
            }
        }
    }

    fun savePlanets() {
        StorageUtils.saveObject(application.applicationContext, PLANETS_LIST, planets)
    }

    private fun restorePlanets(): MutableList<Planet> {
        val obj = StorageUtils.recoverObject<MutableList<Planet>>(application.applicationContext, PLANETS_LIST)
        obj?.let { return it }?: return mutableListOf()
    }
}