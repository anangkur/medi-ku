package com.anangkur.mediku.feature.profile.userProfile

interface ProfileActionListener {
    fun onClickEditProfile()
    fun onClickEditPassword()
    fun onClickLogout()
    fun onClickImage(imageUrl: String)
}