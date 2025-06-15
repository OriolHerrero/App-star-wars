package com.starwars.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starwars.app.business.SWBusiness
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlanetsViewModel @Inject constructor(private val swBusiness: SWBusiness): ViewModel() {

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val planetsJson = swBusiness.loadPlanets()
        }
    }
}