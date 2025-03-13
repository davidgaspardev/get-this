package dev.davidgaspar.getthis.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface DownloadApi {
    @GET
    suspend fun fromUrl(@Url url: String): Response<ResponseBody>
}