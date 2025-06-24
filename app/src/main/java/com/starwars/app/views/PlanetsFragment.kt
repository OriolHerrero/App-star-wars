package com.starwars.app.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.starwars.app.R
import com.starwars.app.base.BaseFragment
import com.starwars.app.utils.StorageUtils
import com.starwars.app.viewmodel.PlanetsViewModel
import com.starwars.app.views.model.PlanetsAdapter
import java.io.IOException
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData()

        viewModel.planetsUpdated.observe(viewLifecycleOwner) {
            context?.let { context ->
                recycler?.adapter = PlanetsAdapter(context, viewModel.planets)
                recycler?.adapter?.notifyDataSetChanged()
            }
        }

        context?.let {
            recycler?.layoutManager = LinearLayoutManager(context)
            recycler?.addOnScrollListener(object: OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    if (layoutManager.findFirstVisibleItemPosition() + layoutManager.childCount >= layoutManager.itemCount) {
                        try {
                            viewModel.loadMoreData()
                        } catch (e: IOException) {
                            recycler?.removeOnScrollListener(this)
                        }
                    }
                }
            })
        }

        testButton?.setOnClickListener {
            context?.let { it1 ->
                StorageUtils.removeObject(it1, "planetsList")
                StorageUtils.removeObject(it1, "next")
                StorageUtils.removeObject(it1, "previous")
            }
        }
    }
}