package dev.davidgaspar.getthis.services.utils

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import dev.davidgaspar.getthis.R

const val DOWNLOAD_DETAILS_CHANNEL_ID = "download_details_channel";
const val NOTIFICATION_ID = 0

fun NotificationManager.sendDownloadNotification(
    applicationContext: Context,
    message: String,
    filePath: String,
) {
    val contentPendingIntent = NavDeepLinkBuilder(applicationContext)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.detail_fragment)
        .setArguments(Bundle().apply {
            putString("filePath", filePath)
        })
        .createPendingIntent()

    val notification = NotificationCompat.Builder(
        applicationContext,
        DOWNLOAD_DETAILS_CHANNEL_ID)
        .setSmallIcon(android.R.drawable.sym_def_app_icon)
        .setContentText(message)
        .setContentIntent(contentPendingIntent)
        .build()

    notify(NOTIFICATION_ID, notification)
}