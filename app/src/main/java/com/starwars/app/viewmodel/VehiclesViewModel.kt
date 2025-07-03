package com.starwars.app.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.starwars.app.base.BaseViewModel
import com.starwars.app.business.SWBusiness
import com.starwars.app.business.models.SingleVehicleBody
import com.starwars.app.utils.StorageUtils
import com.starwars.app.views.model.Planet
import com.starwars.app.views.model.Vehicle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class VehiclesViewModel @Inject constructor(private val swBusiness: SWBusiness, private val application: Application): BaseViewModel(application) {
    var vehicleCalls: List<String>? = null

    private var vehicles: MutableList<Vehicle> = mutableListOf()
    private val _vehiclesUpdated = MutableLiveData<Map<String, List<Vehicle>>>()
    val vehiclesUpdated: LiveData<Map<String, List<Vehicle>>> get() = _vehiclesUpdated

    fun loadData() {
        vehicleCalls?.let { vehicleCalls ->
            for (element in vehicleCalls) {
                val vehicle = restoreVehicle(element.substringAfterLast("/").toInt())
                if (vehicle != null) {
                    vehicles.add(vehicle)
                    _vehiclesUpdated.postValue(vehicles.groupBy { it2-> it2.vehicleClass })
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        swBusiness.loadVehicle(element, object: Callback<SingleVehicleBody> {
                            override fun onResponse(call: Call<SingleVehicleBody>, response: Response<SingleVehicleBody>) {
                                response.body()?.let { body ->
                                    val identifier = body.result.uid
                                    body.result.properties.let {
                                        val v = Vehicle(identifier, it.name, it.vehicle_class, it.cargo_capacity, it.max_atmosphering_speed)
                                        vehicles.add(v)
                                        saveData("vehicle$identifier", v)
                                    }
                                    _vehiclesUpdated.postValue(vehicles.groupBy { it2-> it2.vehicleClass })
                                }
                            }

                            override fun onFailure(call: Call<SingleVehicleBody>, t: Throwable) {
                                _vehiclesUpdated.postValue(emptyMap())
                            }
                        })
                    }
                }
            }
        }
    }

    private fun restoreVehicle(identifier: Int?): Vehicle? {
        if (identifier == null) return null
        return StorageUtils.recoverObject<Vehicle>(application.applicationContext, "vehicle$identifier")
    }
}