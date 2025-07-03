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
import com.starwars.app.viewmodel.FilmsViewModel
import com.starwars.app.views.model.Film
import com.starwars.app.views.model.FilmsAdapter
import java.util.ArrayList
import javax.inject.Inject

class FilmsFragment: BaseFragment() {

    private var recycler: RecyclerView? = null

    override fun bindDaggerInjection() = appComponent.inject(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FilmsViewModel by viewModels { viewModelFactory }

    private val itemClickedListener = object: FilmsAdapter.ItemClickedListener {
        override fun onItemClick(film: Film) {
            val fragment = VehiclesFragment()
            val args = Bundle()
            args.putStringArrayList("vehicles", ArrayList(film.vehicles))
            fragment.arguments = args
            replaceFragment(fragment, R.id.container)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.films_fragment, container, false)
        recycler = view.findViewById(R.id.film_recycler)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filmsUpdated.observe(viewLifecycleOwner) { items ->
            updateItemList(items)
        }
        viewModel.loadFilms()
        context?.let { recycler?.layoutManager = LinearLayoutManager(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateItemList(items: MutableList<Film>) {
        context?.let { context ->
            recycler?.adapter = FilmsAdapter(context, items, itemClickedListener)
            recycler?.adapter?.notifyDataSetChanged()
        }
    }
}