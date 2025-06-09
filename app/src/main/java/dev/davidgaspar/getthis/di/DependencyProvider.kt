package dev.davidgaspar.getthis.di

import android.content.Context
import dev.davidgaspar.getthis.data.api.DownloadApi
import dev.davidgaspar.getthis.data.repository.FileRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object DependencyProvider {
    private val okHttClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(okHttClient)
            .build()
    }

    fun getFileRepository(context: Context): FileRepository {
        return FileRepository(
            retrofit.create(DownloadApi::class.java),
            context
        )
    }
}