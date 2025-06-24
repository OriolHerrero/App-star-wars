package com.starwars.app.utils

import android.content.Context
import com.google.gson.Gson
import androidx.core.content.edit
import com.google.gson.reflect.TypeToken

class StorageUtils {

    companion object {

        fun saveObject(context: Context, id: String, obj: Any) {
            val json = Gson().toJson(obj)
            val sharedPreferences =
                context.getSharedPreferences("PersistentData", Context.MODE_PRIVATE)
            sharedPreferences.edit { putString(id, json) }
        }

        inline fun <reified T> recoverObject(context: Context, id: String): T? {
            val sharedPreferences =
                context.getSharedPreferences("PersistentData", Context.MODE_PRIVATE)
            val json = sharedPreferences.getString(id, null)
            val type = object : TypeToken<T>() {}.type
            return Gson().fromJson(json, type)
        }
    }
}