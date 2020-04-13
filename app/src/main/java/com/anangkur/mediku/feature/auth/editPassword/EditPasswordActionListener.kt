package com.anangkur.mediku.feature.auth.editPassword

interface EditPasswordActionListener {
    fun onClickSave(oldPassword: String?, newPassword: String?, confirmPassword: String?)
}