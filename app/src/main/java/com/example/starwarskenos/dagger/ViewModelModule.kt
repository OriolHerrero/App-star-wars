package com.example.starwarskenos.dagger

import com.example.starwarskenos.viewmodel.OverviewViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesOverviewViewModel(): OverviewViewModel = OverviewViewModel()
}