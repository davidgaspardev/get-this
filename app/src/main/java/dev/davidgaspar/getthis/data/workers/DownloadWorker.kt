package dev.davidgaspar.getthis.data.workers

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import dev.davidgaspar.getthis.data.model.DownloadInfo
import dev.davidgaspar.getthis.di.DependencyProvider
import dev.davidgaspar.getthis.services.utils.sendDownloadNotification

class DownloadWorker(
    private val context: Context,
    params: WorkerParameters
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val downloadInfoJson = inputData.getString("downloadInfoJson") ?: return Result.failure()
        val downloadInfo = Gson().fromJson(downloadInfoJson, DownloadInfo::class.java)
        val imageRepository = DependencyProvider.getImageRepository(context)

        return try {
            val imagePath = imageRepository.download(downloadInfo)
            downloadInfo.filePath = imagePath.absolutePath
            downloadInfo.status = "Success"

            Result.success()
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error downloading image", e)
            downloadInfo.status = "Failed"

            Result.failure()
        } finally {
            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.sendDownloadNotification(
                context,
                downloadInfo
            )
        }
    }

    companion object {
        const val LOG_TAG = "DownloadWorker"
    }
}