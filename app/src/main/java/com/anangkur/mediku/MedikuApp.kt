package com.anangkur.mediku

import android.app.Application
import android.util.Log
import com.anangkur.mediku.util.ForceUpdateChecker
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


class MedikuApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
//        val remoteConfigDefaults: MutableMap<String, Any> = HashMap()
//        remoteConfigDefaults[ForceUpdateChecker.KEY_UPDATE_REQUIRED] = false
//        remoteConfigDefaults[ForceUpdateChecker.KEY_CURRENT_VERSION] = "1.1"
//        remoteConfigDefaults[ForceUpdateChecker.KEY_UPDATE_URL] = "https://play.google.com/store/apps/details?id=com.anangkur.mediku"
//        firebaseRemoteConfig.setDefaults(remoteConfigDefaults)
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