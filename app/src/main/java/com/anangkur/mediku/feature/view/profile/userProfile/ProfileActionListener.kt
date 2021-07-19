package com.anangkur.mediku.feature.view.profile.userProfile

interface ProfileActionListener {
    fun onClickEditProfile()
    fun onClickEditPassword()
    fun onClickLogout()
    fun onCLickAbout()
    fun onClickImage(imageUrl: String)
}