package com.starwars.app

import android.app.Application
import com.starwars.app.dagger.ApplicationComponent
import com.starwars.app.dagger.DaggerApplicationComponent
import com.starwars.app.dagger.RetrofitModule

class StarWarsApplication: Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .retrofitModule(RetrofitModule(this))
            .build()

        appComponent.inject(this)
    }
}