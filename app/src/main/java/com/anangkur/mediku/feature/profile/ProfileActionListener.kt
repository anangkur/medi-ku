package com.anangkur.mediku.feature.profile

interface ProfileActionListener {
    fun onClickEditProfile()
    fun onClickEditPassword()
    fun onClickLogout()
    fun onClickImage(imageUrl: String)
}