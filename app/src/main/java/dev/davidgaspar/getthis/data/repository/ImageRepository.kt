package dev.davidgaspar.getthis.data.repository

import android.content.Context
import android.os.Environment
import android.util.Log
import dev.davidgaspar.getthis.data.api.DownloadApi
import dev.davidgaspar.getthis.data.model.DownloadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class ImageRepository (
    private val downloadApi: DownloadApi,
    private val context: Context
) {
    suspend fun download(downloadInfo: DownloadInfo): File {
        val response = downloadApi.fromUrl(downloadInfo.url)
        if (!response.isSuccessful) {
            throw Exception("Error downloading file (url: ${downloadInfo.url}): ${response.code()} ${response.message()}")
        }

        val responseBody = response.body() ?: throw Exception("Response body is null")
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
            downloadInfo.name
        )

        saveImageByteToFile(responseBody.byteStream(), file)

        return file
    }

    private suspend fun saveImageByteToFile(inputStream: InputStream, file: File) {
            return withContext(Dispatchers.IO) {
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
    }

    companion object {
        private const val TAG = "ImageRepository"
    }
}