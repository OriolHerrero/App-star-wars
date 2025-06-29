package com.starwars.app.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.starwars.app.utils.StorageUtils

abstract class BaseViewModel(private val application: Application): AndroidViewModel(application) {

    protected fun saveData(id: String, data: Any) {
        StorageUtils.saveObject(application.applicationContext, id, data)
    }
}