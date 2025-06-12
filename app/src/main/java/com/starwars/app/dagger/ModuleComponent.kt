package com.starwars.app.dagger

import com.starwars.app.views.PlanetsFragment
import dagger.Subcomponent

@Subcomponent
interface ModuleComponent {
    fun inject(obj: PlanetsFragment)
}