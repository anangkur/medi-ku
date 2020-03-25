package com.anangkur.mediku.feature.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.anangkur.mediku.R
import com.anangkur.mediku.feature.splash.SplashActivity
import com.anangkur.mediku.util.Const

class NotificationHelper(private val context: Context){

    fun createNotification(title: String, message: String, itemId: Int, bitmap: Bitmap?) {
        if (bitmap != null){
            showNotification(title, itemId, buildNotification(createIntent(), title, message))
        }else{
            showNotification(title, itemId, buildNotificationWithImage(createIntent(), title, message, bitmap))
        }
    }

    private fun showNotification(title: String, itemId: Int, mBuilder: NotificationCompat.Builder){
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannelId = Const.NOTIFICATION_CHANNEL_ID

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(notificationChannelId, title, importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.WHITE
            notificationChannel.enableVibration(true)

            mBuilder.setChannelId(notificationChannelId)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }

        mNotificationManager.notify(itemId, mBuilder.build())
    }

    private fun createIntent(): PendingIntent {
        val resultIntent = Intent(context, SplashActivity::class.java)

        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    private fun buildNotification(pendingIntent: PendingIntent, title: String, message: String): NotificationCompat.Builder{
        return NotificationCompat.Builder(context, Const.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_healthy)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    private fun buildNotificationWithImage(pendingIntent: PendingIntent, title: String, message: String, image: Bitmap?): NotificationCompat.Builder{
        return NotificationCompat.Builder(context, Const.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_healthy)
            .setLargeIcon(image)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigLargeIcon(null)
                .bigPicture(image))
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }
}