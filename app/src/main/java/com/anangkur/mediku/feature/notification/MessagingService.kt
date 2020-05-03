package com.anangkur.mediku.feature.notification

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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

        if (image.toString().isNullOrEmpty()) {
            Glide.with(this)
                .asBitmap()
                .load(image)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) { bitmap = resource }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }

        NotificationHelper(this).createNotification(title, message, id.toInt(), bitmap, type)
    }
}