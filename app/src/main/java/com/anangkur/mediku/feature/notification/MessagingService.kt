package com.anangkur.mediku.feature.notification

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
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

        Log.d("MessagingService", "From: ${remoteMessage.from}")
        Log.d("MessagingService", "data: ${remoteMessage.data}")
        Log.d("MessagingService", "notification: ${remoteMessage.notification}")

        val title = remoteMessage.notification?.title?:""
        val message = remoteMessage.notification?.body?:""
        val type = remoteMessage.data["type"] ?: ""
        val id = remoteMessage.data["id"] ?: "0"
        val image = remoteMessage.notification?.imageUrl

        var bitmap: Bitmap? = null

        NotificationHelper(this).createNotification(title, message, id.toInt(), bitmap, type)
    }
}