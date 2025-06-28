package com.starwars.app.views

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
import com.starwars.app.views.model.FilmsAdapter
import javax.inject.Inject

class FilmsFragment: BaseFragment() {

    private var recycler: RecyclerView? = null

    override fun bindDaggerInjection() = appComponent.inject(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FilmsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.films_fragment, container, false)
        recycler = view.findViewById(R.id.film_recycler)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filmsUpdated.observe(viewLifecycleOwner) {

        }
        viewModel.loadFilms()

        context?.let {
            recycler?.layoutManager = LinearLayoutManager(it)
            recycler?.adapter = FilmsAdapter(it, viewModel.films)
        }
    }
}