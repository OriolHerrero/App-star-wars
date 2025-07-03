package com.starwars.app.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.starwars.app.base.BaseViewModel
import com.starwars.app.business.SWBusiness
import com.starwars.app.utils.StorageUtils
import com.starwars.app.views.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class FilmsViewModel @Inject constructor(private val swBusiness: SWBusiness, private val application: Application): BaseViewModel(application) {

    private val filmslist = "filmslist"

    private var films: MutableList<Film> = mutableListOf()
    private val _filmsUpdated = MutableLiveData<MutableList<Film>>()
    val filmsUpdated: LiveData<MutableList<Film>> get() = _filmsUpdated
    
    fun loadFilms() {
        films = restoreFilms()
        if (films.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val body = swBusiness.loadFilms()
                    body?.let {
                        for (element in it.result) {
                            films.add(Film(element.uid, element.properties.title, element.properties.episode_id,
                                element.properties.director, element.properties.vehicles))
                        }
                        saveData(filmslist, films)
                        _filmsUpdated.postValue(films)
                    }
                } catch (e: IOException) {
                    _filmsUpdated.postValue(mutableListOf())
                }
            }
        } else {
            _filmsUpdated.postValue(films)
        }
    }

    private fun restoreFilms(): MutableList<Film> {
        val obj = StorageUtils.recoverObject<MutableList<Film>>(application.applicationContext, filmslist)
        return obj ?: mutableListOf()
    }
}