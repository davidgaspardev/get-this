package dev.davidgaspar.getthis.di

import dev.davidgaspar.getthis.data.api.DownloadApi
import dev.davidgaspar.getthis.data.repository.ImageRepository
import retrofit2.Retrofit

class DependencyProvider {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://github.com")
        .build()

    private val downloadApi = retrofit.create(DownloadApi::class.java)

    val imageRepository = ImageRepository(downloadApi)
}