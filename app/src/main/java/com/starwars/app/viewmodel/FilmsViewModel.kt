package com.starwars.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.starwars.app.business.SWBusiness
import com.starwars.app.views.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class FilmsViewModel @Inject constructor(private val swBusiness: SWBusiness, private val application: Application): AndroidViewModel(application) {

    var films: MutableList<Film> = mutableListOf()
    private val _filmsUpdated = MutableLiveData<MutableList<Film>>()
    val filmsUpdated: LiveData<MutableList<Film>> get() = _filmsUpdated
    
    fun loadFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val body = swBusiness.loadFilms()
                body?.let {
                    for (element in it.result) {
                        films.add(Film(element.uid, element.properties.title, element.properties.episode_id, element.properties.director))
                    }
                    _filmsUpdated.postValue(films)
                }
            } catch (e: IOException) {
                _filmsUpdated.postValue(mutableListOf())
            }
        }
    }
}