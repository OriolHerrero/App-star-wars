package com.starwars.app.dagger

import com.starwars.app.business.SWBusiness
import com.starwars.app.business.SWBusinessImpl
import com.starwars.app.rest.ApiRepositorySW
import com.starwars.app.viewmodel.PlanetsViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesSWBusiness(apiRepositorySW: ApiRepositorySW): SWBusiness {
        return SWBusinessImpl(apiRepositorySW)
    }

    @Provides
    fun providesPlanetsViewModel(swBusiness: SWBusiness): PlanetsViewModel = PlanetsViewModel(swBusiness)
}