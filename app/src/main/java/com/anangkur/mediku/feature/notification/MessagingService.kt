package com.anangkur.mediku.feature.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService: FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.d("OnNewToken", "token firebase: $p0")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("MessagingService", "From: " + remoteMessage.from)
        Log.d("MessagingService", "From: " + remoteMessage.data)

        val tempTitle = remoteMessage.notification?.title?:""
        val tempMessage = remoteMessage.notification?.body?:""
        val tempItemId = remoteMessage.data["id"] ?: ""
        Log.d("MessagingService", "tempTitle: $tempTitle, tempMessage: $tempMessage, tempItemId: $tempItemId")

        NotificationHelper(this).createNotification(tempTitle, tempMessage, tempItemId.toInt(), null)
    }
}