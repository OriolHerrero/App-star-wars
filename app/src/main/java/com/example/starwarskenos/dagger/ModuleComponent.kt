package com.example.starwarskenos.dagger

import com.example.starwarskenos.views.OverviewFragment
import dagger.Subcomponent

@Subcomponent
interface ModuleComponent {
    fun inject(obj: OverviewFragment)
}