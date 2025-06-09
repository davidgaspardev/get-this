package dev.davidgaspar.getthis.data.repository

import android.content.Context
import android.os.Environment
import android.util.Log
import dev.davidgaspar.getthis.data.api.DownloadApi
import dev.davidgaspar.getthis.data.model.DownloadInfo
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class FileRepository (
    private val downloadApi: DownloadApi,
    private val context: Context
) {
    suspend fun download(downloadInfo: DownloadInfo): File {
        Log.d(TAG, "Downloading image from ${downloadInfo.url}")
        val response = downloadApi.fromUrl(downloadInfo.url)
        if (!response.isSuccessful) {
            throw Exception("Error downloading file (url: ${downloadInfo.url}): ${response.code()} ${response.message()}")
        }

        val responseBody = response.body() ?: throw Exception("Response body is null")
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            downloadInfo.name
        )

        saveResponseToFile(responseBody.byteStream(), file)

        return file
    }

    private fun saveResponseToFile(inputStream: InputStream, file: File) {
        Log.d(TAG, "Saving file to ${file.absolutePath}")

        try {
            val outputStream: OutputStream = FileOutputStream(file)

            val buffer = ByteArray(4096)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it ?: -1 } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            inputStream.close()
            outputStream.close()

        } catch (e: Exception) {
            Log.e("Download", "Error downloading file", e)
        }
    }

    companion object {
        private const val TAG = "ImageRepository"
    }
}