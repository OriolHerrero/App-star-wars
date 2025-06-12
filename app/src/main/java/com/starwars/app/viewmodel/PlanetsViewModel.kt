package com.starwars.app.viewmodel

import androidx.lifecycle.ViewModel
import com.starwars.app.business.SWBusiness
import javax.inject.Inject

class PlanetsViewModel @Inject constructor(private val swBusiness: SWBusiness): ViewModel() {

    fun loadData() {
        val a = swBusiness.loadPlanets()
    }
}