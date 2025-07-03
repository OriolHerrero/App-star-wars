package com.starwars.app.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starwars.app.R
import com.starwars.app.base.BaseFragment
import com.starwars.app.viewmodel.VehiclesViewModel
import com.starwars.app.views.model.Vehicle
import com.starwars.app.views.model.VehicleAdapterItem
import com.starwars.app.views.model.VehiclesAdapter
import javax.inject.Inject

class VehiclesFragment: BaseFragment() {

    private var recycler: RecyclerView? = null

    override fun bindDaggerInjection() = appComponent.inject(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: VehiclesViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey("vehicles")) {
                viewModel.vehicleCalls = it.getStringArrayList("vehicles")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.vehicles_fragment, container, false)
        recycler = view.findViewById(R.id.vehicle_recycler)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.vehiclesUpdated.observe(viewLifecycleOwner) { items ->
            updateItemList(items)
        }
        viewModel.loadData()
        context?.let { recycler?.layoutManager = LinearLayoutManager(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateItemList(items: Map<String, List<Vehicle>>) {
        context?.let { context ->
            val list = mutableListOf<VehicleAdapterItem>()
            for ((key, elements) in items) {
                list.add(VehicleAdapterItem.Header(key))
                list.addAll(elements.map { VehicleAdapterItem.Content(it.name, it.cargoCapacity, it.maxAtmosphericSpeed) })
            }
            recycler?.adapter = VehiclesAdapter(context, list)
            recycler?.adapter?.notifyDataSetChanged()
        }
    }
}