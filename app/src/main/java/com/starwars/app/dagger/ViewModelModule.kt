package com.starwars.app.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.starwars.app.viewmodel.FilmsViewModel
import com.starwars.app.viewmodel.PlanetsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PlanetsViewModel::class)
    abstract fun bindPlanetsViewModel(viewModel: PlanetsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilmsViewModel::class)
    abstract fun bindFilmsViewModel(viewModel: FilmsViewModel): ViewModel

}