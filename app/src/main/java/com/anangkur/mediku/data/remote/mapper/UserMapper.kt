package com.anangkur.mediku.data.remote.mapper

import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.remote.model.auth.UserRemoteModel

class UserMapper: RemoteMapper<UserRemoteModel, User> {

    companion object{
        private var INSTANCE: UserMapper? = null
        fun getInstance() = INSTANCE ?: UserMapper()
    }

    override fun mapFromRemote(data: UserRemoteModel): User {
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

    override fun mapToRemote(data: User): UserRemoteModel {
        return UserRemoteModel(
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