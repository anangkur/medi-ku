package com.anangkur.mediku.feature.editProfile

interface EditProfileActionListener {
    fun onClickSave(name: String, height: String, weight: String)
    fun onCLickImage()
}