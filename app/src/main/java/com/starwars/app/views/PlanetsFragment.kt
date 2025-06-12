package com.starwars.app.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.starwars.app.base.BaseFragment
import com.starwars.app.databinding.FragmentFirstBinding
import com.starwars.app.rest.ApiRepositorySW
import com.starwars.app.viewmodel.PlanetsViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlanetsFragment : BaseFragment() {

    private var viewModel: PlanetsViewModel? = null

    @Inject
    lateinit var apiRepositorySW: ApiRepositorySW

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun bindDaggerInjection() = appComponent.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        activity?.let {
            viewModel = ViewModelProvider(it)[PlanetsViewModel::class.java]
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val res = viewModel?.loadData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}