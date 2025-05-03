package dev.davidgaspar.getthis.data.workers

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.davidgaspar.getthis.di.DependencyProvider
import dev.davidgaspar.getthis.services.utils.sendDownloadNotification

class DownloadWorker(
    private val context: Context,
    params: WorkerParameters
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val imageRepository = DependencyProvider.getImageRepository(context)
        val imageUrl = inputData.getString("url") ?: return Result.failure()

        return try {
            val imagePath = imageRepository.download(imageUrl)

            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.sendDownloadNotification(
                context,
                "Download complete",
                imagePath,
            )

            Result.success()
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error downloading image", e)
            Result.failure()
        }
    }

    companion object {
        const val LOG_TAG = "DownloadWorker"
    }
}