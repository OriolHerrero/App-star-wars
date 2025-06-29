package com.starwars.app.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.starwars.app.base.BaseViewModel
import com.starwars.app.business.SWBusiness
import com.starwars.app.business.models.PlanetListBody
import com.starwars.app.business.models.SinglePlanetBody
import com.starwars.app.utils.StorageUtils
import com.starwars.app.views.model.Planet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PlanetsViewModel @Inject constructor(private val swBusiness: SWBusiness, private val application: Application): BaseViewModel(application) {

    private val planetlist = "planetsList"
    private val nextCall = "next"
    private val previousCall = "previous"

    var planets: MutableList<Planet> = mutableListOf()
    private val _planetsUpdated = MutableLiveData<MutableList<Planet>>()
    val planetsUpdated: LiveData<MutableList<Planet>> get() = _planetsUpdated


    fun loadData() {
        planets = restorePlanets()
        if (planets.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val body = swBusiness.loadPlanets()
                    parseData(body)
                } catch (e: IOException) {
                    _planetsUpdated.postValue(mutableListOf())
                }

            }
        } else {
            _planetsUpdated.postValue(planets)
        }
    }

    fun loadMoreData() {
        val url = StorageUtils.recoverObject<String>(application.applicationContext, nextCall)
        val previousUrl = StorageUtils.recoverObject<String>(application.applicationContext, previousCall)
        if (url?.isNotEmpty() == true && url != previousUrl) {
            saveData(previousCall, url)
            viewModelScope.launch(Dispatchers.IO) {
                val body = swBusiness.loadAdditionalPlanets(url)
                parseData(body)
            }
        }
    }

    private fun parseData(body: PlanetListBody?) {
        body?.let {
            it.next?.let { it1 -> saveData(nextCall, it1) } ?: saveData(nextCall, "")
            for (element in it.results) {
                swBusiness.loadPlanet(element.url, object: Callback<SinglePlanetBody> {
                    override fun onResponse(call: Call<SinglePlanetBody>, response: Response<SinglePlanetBody>) {
                        response.body()?.result?.let { result ->
                            planets.add(Planet(result.uid, result.properties.name, result.properties.population, result.properties.diameter))
                            planets.sortBy { element-> element.id }
                            saveData(planetlist, planets)
                            _planetsUpdated.postValue(planets)
                        }
                    }

                    override fun onFailure(call: Call<SinglePlanetBody>, t: Throwable) { }
                })
            }
        }
    }

    private fun restorePlanets(): MutableList<Planet> {
        val obj = StorageUtils.recoverObject<MutableList<Planet>>(application.applicationContext, planetlist)
        return obj ?: mutableListOf()
    }
}