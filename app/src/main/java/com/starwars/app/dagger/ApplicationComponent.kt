package com.starwars.app.dagger

import com.starwars.app.StarWarsApplication
import com.starwars.app.views.FilmsFragment
import com.starwars.app.views.PlanetsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: StarWarsApplication)
    fun inject(planetsFragment: PlanetsFragment)
    fun inject(filmsFragment: FilmsFragment)
}