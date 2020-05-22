package com.anangkur.mediku

import android.app.Application
import android.util.Log
import com.anangkur.mediku.util.ForceUpdateChecker
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


class MedikuApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.fetch(60) // fetch every minutes
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "remote config is fetched.")
                    firebaseRemoteConfig.activateFetched()
                }
            }
    }

    companion object {
        private val TAG = MedikuApp::class.java.simpleName
    }
}