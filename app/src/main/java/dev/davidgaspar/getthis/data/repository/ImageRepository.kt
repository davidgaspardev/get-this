package dev.davidgaspar.getthis.data.repository

import android.content.Context
import android.os.Environment
import android.util.Log
import dev.davidgaspar.getthis.data.api.DownloadApi
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
    suspend fun download(url: String, fileName: String = url.split("/").last()): String {
        val response = downloadApi.fromUrl(url)
        if (!response.isSuccessful) {
            throw Exception("Error downloading file (url: ${url}): ${response.code()} ${response.message()}")
        }

        val responseBody = response.body() ?: throw Exception("Response body is null")
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)

        saveImageByteToFile(responseBody.byteStream(), file)

        return file.absolutePath
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