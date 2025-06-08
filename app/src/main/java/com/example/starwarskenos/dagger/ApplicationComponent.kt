package com.example.starwarskenos.dagger

import android.app.Application
import com.example.starwarskenos.rest.ApiRepositorySW
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, ViewModelModule::class])

interface ApplicationComponent {
    fun inject(obj: StarWarsApplication)
    fun inject(obj: ApiRepositorySW)
}

class StarWarsApplication: Application() {
    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .retrofitModule(RetrofitModule("https://www.swapi.tech/api/"))
            .build()

        appComponent.inject(this)
    }
}