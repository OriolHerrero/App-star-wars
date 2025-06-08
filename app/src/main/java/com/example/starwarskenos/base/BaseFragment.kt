package com.example.starwarskenos.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<VM: ViewModel>: Fragment() {

    @Inject
    lateinit var viewModel: VM
    protected abstract val viewModelClass: KClass<VM>
    protected abstract val viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDaggerInjection()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass.java]
    }

    abstract fun bindDaggerInjection()
}