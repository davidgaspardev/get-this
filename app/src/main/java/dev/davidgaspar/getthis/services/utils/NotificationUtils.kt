package dev.davidgaspar.getthis.services.utils

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.gson.Gson
import dev.davidgaspar.getthis.R
import dev.davidgaspar.getthis.data.model.DownloadInfo

const val DOWNLOAD_DETAILS_CHANNEL_ID = "download_details_channel"

fun NotificationManager.sendDownloadNotification(
    applicationContext: Context,
    downloadInfo: DownloadInfo
) {
    val notificationId = downloadInfo.name.hashCode()
    val downloadInfoJson = Gson().toJson(downloadInfo)
    val contentPendingIntent = NavDeepLinkBuilder(applicationContext)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.detail_activity)
        .setArguments(Bundle().apply {
            putString("downloadInfoJson", downloadInfoJson)
        })
        .createPendingIntent()

    val notification = NotificationCompat.Builder(
        applicationContext,
        DOWNLOAD_DETAILS_CHANNEL_ID)
        .setSmallIcon(android.R.drawable.sym_def_app_icon)
        .setContentText(downloadInfo.name)
        .setContentIntent(contentPendingIntent)
        .build()

    notify(notificationId, notification)
}