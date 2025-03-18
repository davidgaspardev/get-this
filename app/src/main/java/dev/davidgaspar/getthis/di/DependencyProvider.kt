package dev.davidgaspar.getthis.di

import android.app.Application
import dev.davidgaspar.getthis.data.api.DownloadApi
import dev.davidgaspar.getthis.data.repository.ImageRepository
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

    fun getImageRepository(application: Application): ImageRepository {
        return ImageRepository(
            retrofit.create(DownloadApi::class.java),
            application
        )
    }
}