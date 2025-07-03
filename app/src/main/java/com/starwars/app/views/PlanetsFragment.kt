package com.starwars.app.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.starwars.app.R
import com.starwars.app.base.BaseFragment
import com.starwars.app.utils.StorageUtils
import com.starwars.app.viewmodel.PlanetsViewModel
import com.starwars.app.views.model.Planet
import com.starwars.app.views.model.PlanetsAdapter
import java.io.IOException
import javax.inject.Inject


class PlanetsFragment : BaseFragment() {

    private var recycler: RecyclerView? = null
    private var clearDataButton: Button? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PlanetsViewModel by viewModels { viewModelFactory }

    override fun bindDaggerInjection() = appComponent.inject(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.planets_fragment, container, false)
        recycler = view.findViewById(R.id.planet_recycler)
        clearDataButton = view.findViewById(R.id.clear_data_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData()
        setupMenu()

        viewModel.planetsUpdated.observe(viewLifecycleOwner) {
            updateItemList(viewModel.planets)
        }

        context?.let { context ->
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

            clearDataButton?.setOnClickListener {
                StorageUtils.clearAllData(context)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateItemList(items: MutableList<Planet>) {
        context?.let { context ->
            recycler?.adapter = PlanetsAdapter(context, items)
            recycler?.adapter?.notifyDataSetChanged()
        }
    }

    private fun setupMenu() {
        activity?.let {
            val menuHost: MenuHost = it
            menuHost.addMenuProvider(object: MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.search_bar, menu)
                    val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                    searchView.setOnQueryTextListener(object: OnQueryTextListener {
                        override fun onQueryTextChange(newText: String?): Boolean {
                            if (!newText.isNullOrEmpty()) {
                                updateItemList(viewModel.planets.filter { element -> element.name.contains(newText, true) }.toMutableList())
                            } else {
                                updateItemList(viewModel.planets)
                            }
                            return true
                        }

                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return true
                        }
                    })
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return false
                }

            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }
}