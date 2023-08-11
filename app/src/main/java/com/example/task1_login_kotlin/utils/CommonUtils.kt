package com.example.task1_login_kotlin.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.task1_login_kotlin.App


class CommonUtils {
    companion object{
        val instance: CommonUtils = CommonUtils()
    }
    private val PREF_NAME: String = "pref_save"

    fun savePref(key: String, data: String) {
        val pref: SharedPreferences =
            App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putString(key, data).apply()
    }

    fun getPref(key: String): String? {
        val pref: SharedPreferences =
            App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getString(key, null)
    }

    fun clearPref(key: String) {
        val pref: SharedPreferences =
            App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().remove(key).apply()
    }

}