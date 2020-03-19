package com.anangkur.mediku.feature.editPassword

interface EditPasswordActionListener {
    fun onClickSave(oldPassword: String?, newPassword: String?, confirmPassword: String?)
}