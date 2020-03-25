package com.anangkur.mediku.data.remote

import com.anangkur.mediku.base.BaseDataSource
import com.anangkur.mediku.data.DataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class RemoteRepository(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val storage: FirebaseStorage
): DataSource, BaseDataSource() {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance(
            firebaseAuth: FirebaseAuth,
            firestore: FirebaseFirestore,
            storage: FirebaseStorage
        ) = INSTANCE ?: RemoteRepository(firebaseAuth, firestore, storage)
    }
}