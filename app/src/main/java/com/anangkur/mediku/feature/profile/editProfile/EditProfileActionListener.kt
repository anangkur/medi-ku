package com.anangkur.mediku.feature.profile.editProfile

interface EditProfileActionListener {
    fun onClickSave(name: String, height: String, weight: String)
    fun onCLickImage()
}