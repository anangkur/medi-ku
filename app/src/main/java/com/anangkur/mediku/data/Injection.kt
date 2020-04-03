package com.anangkur.mediku.data

import android.content.Context
import android.content.SharedPreferences
import com.anangkur.mediku.data.local.LocalRepository
import com.anangkur.mediku.data.local.room.AppDao
import com.anangkur.mediku.data.remote.Covid19ApiService
import com.anangkur.mediku.data.remote.NewCovid19ApiService
import com.anangkur.mediku.data.remote.RemoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object Injection {
    fun provideRepository(
        preferences: SharedPreferences,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        storage: FirebaseStorage,
        dao: AppDao,
        covid19ApiService: Covid19ApiService,
        newCovid19ApiService: NewCovid19ApiService
    ) = Repository.getInstance(
        RemoteRepository.getInstance(firebaseAuth, firestore, storage, covid19ApiService, newCovid19ApiService),
        LocalRepository.getInstance(preferences, dao)
    )
}