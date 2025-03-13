package dev.davidgaspar.getthis.data.repository

import dev.davidgaspar.getthis.data.api.DownloadApi
import okhttp3.ResponseBody
import java.io.FileOutputStream
import java.io.InputStream

class ImageRepository constructor(
    private val downloadApi: DownloadApi
) {
    suspend fun downloadImage(url: String) {
        val response = downloadApi.fromUrl(url)
        if (response.isSuccessful) {
            response.body()?.let { responseBody ->
                // Save the image to the device
            }
        }
    }

    private fun saveImageToDisk(resBody: ResponseBody) {
        // Save the image to the device
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null

        try {
            inputStream = resBody.byteStream()
            outputStream = FileOutputStream("image.jpg")
            val buffer = ByteArray(4096)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }
}