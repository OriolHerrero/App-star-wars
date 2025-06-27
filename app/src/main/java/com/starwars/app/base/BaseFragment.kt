package com.starwars.app.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.starwars.app.StarWarsApplication
import com.starwars.app.dagger.ApplicationComponent

abstract class BaseFragment: Fragment() {

    val appComponent: ApplicationComponent
        get() = (requireActivity().application as StarWarsApplication).appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindDaggerInjection()
    }

    fun replaceFragment(fragment: Fragment, container: Int) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(container, fragment, fragment.javaClass.simpleName)
        transaction?.addToBackStack(fragment.javaClass.simpleName)
        transaction?.commitAllowingStateLoss()
    }

    abstract fun bindDaggerInjection()
}