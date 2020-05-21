package com.anangkur.mediku.feature.mapper

import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.feature.model.auth.UserIntent

class UserMapper: IntentMapper<UserIntent, User> {

    companion object{
        private var INSTANCE: UserMapper? = null
        fun getInstance() = INSTANCE ?: UserMapper()
    }

    override fun mapToIntent(data: User): UserIntent {
        return UserIntent(
            userId = data.userId,
            name = data.name,
            firebaseToken = data.firebaseToken,
            weight = data.weight,
            email = data.email,
            height = data.height,
            photo = data.photo,
            providerName = data.providerName
        )
    }

    override fun mapFromIntent(data: UserIntent): User {
        return User(
            userId = data.userId,
            name = data.name,
            firebaseToken = data.firebaseToken,
            weight = data.weight,
            email = data.email,
            height = data.height,
            photo = data.photo,
            providerName = data.providerName
        )
    }
}