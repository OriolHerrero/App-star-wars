package com.starwars.app.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starwars.app.R
import com.starwars.app.base.BaseFragment
import com.starwars.app.viewmodel.PlanetsViewModel
import com.starwars.app.views.model.Planet
import com.starwars.app.views.model.PlanetsAdapter
import javax.inject.Inject


class PlanetsFragment : BaseFragment() {

    private var recycler: RecyclerView? = null
    private var testButton: Button? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PlanetsViewModel by viewModels { viewModelFactory }

    override fun bindDaggerInjection() = appComponent.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.planets_fragment, container, false)
        recycler = view.findViewById(R.id.recycler)
        testButton = view.findViewById(R.id.button_test)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testButton?.setOnClickListener {
            val res = viewModel.loadData()
        }
        //TODO test
        val a = mutableListOf<Planet>()
        a.add(Planet(1, "Primer planeta", 4, 10000.0))
        a.add(Planet(2, "Segon planeta", 16, 20000.0))
        a.add(Planet(3, "Tercer planeta", 50, 200400.0))
        //TODO
        context?.let { context ->
            recycler?.layoutManager = LinearLayoutManager(context)
            recycler?.adapter = PlanetsAdapter(context, a)
        }
    }
}