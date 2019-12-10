package com.anangkur.uangkerja.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.anangkur.uangkerja.data.DataSource
import com.anangkur.uangkerja.util.Const

class LocalRepository(private val context: Context, private val preferences: SharedPreferences): DataSource {

    override fun saveApiToken(apiToken: String) {
        preferences.edit().putString(Const.PREF_TOKEN, apiToken).apply()
    }

    override fun loadApiToken(): String? {
        return preferences.getString(Const.PREF_TOKEN, null)
    }

    override fun deleteApiToken() {
        preferences.edit().remove(Const.PREF_TOKEN).apply()
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: LocalRepository? = null
        fun getInstance(context: Context, preferences: SharedPreferences) = INSTANCE ?: LocalRepository(context, preferences)
    }
}