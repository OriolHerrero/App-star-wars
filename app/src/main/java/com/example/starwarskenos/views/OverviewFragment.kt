package com.example.starwarskenos.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.starwarskenos.base.BaseFragment
import com.example.starwarskenos.databinding.FragmentFirstBinding
import com.example.starwarskenos.rest.ApiRepositorySW
import com.example.starwarskenos.viewmodel.OverviewViewModel
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OverviewFragment : BaseFragment<OverviewViewModel>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override val viewModelClass = OverviewViewModel::class
    override val viewModelFactory: ViewModelProvider.Factory
        get() = TODO("Not yet implemented")

    @Inject
    lateinit var apiRepositorySW: ApiRepositorySW

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun bindDaggerInjection() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val res = apiRepositorySW.people()
        }
    }

    override fun onDestroyView() {
        viewModel.example()
        super.onDestroyView()
        _binding = null
    }
}