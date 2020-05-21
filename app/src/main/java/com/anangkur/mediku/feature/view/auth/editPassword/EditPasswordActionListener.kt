package com.anangkur.mediku.feature.view.auth.editPassword

interface EditPasswordActionListener {
    fun onClickSave(oldPassword: String?, newPassword: String?, confirmPassword: String?)
}