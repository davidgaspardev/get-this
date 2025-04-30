package dev.davidgaspar.getthis.services.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import dev.davidgaspar.getthis.ui.detail.DetailFragment

const val DOWNLOAD_DETAILS_CHANNEL_ID = "download_details_channel";
const val NOTIFICATION_ID = 0

fun NotificationManager.sendDownloadNotification(
    applicationContext: Context,
    message: String,
    filePath: String,
) {
    val contentIntent = Intent(applicationContext, DetailFragment::class.java).apply {
        putExtra("filePath", filePath)
    }

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notification = NotificationCompat.Builder(
        applicationContext,
        DOWNLOAD_DETAILS_CHANNEL_ID)
        .setContentText(message)
        .setContentIntent(contentPendingIntent)
        .build()

    notify(NOTIFICATION_ID, notification)
}