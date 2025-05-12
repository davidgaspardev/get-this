package dev.davidgaspar.getthis.data.config

import dev.davidgaspar.getthis.R
import dev.davidgaspar.getthis.data.model.DownloadInfo

val radioButtonDownloadInfoMap = mapOf(
    R.id.radio_glide to DownloadInfo(
        name = "Glide - Image Loading Library by BumpTech",
        url = "https://github.com/bumptech/glide"
    ),
    R.id.radio_loadapp to DownloadInfo(
        name = "LoadApp - Current repository by Udacity",
        url = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter"
    ),
    R.id.radio_retrofit to DownloadInfo(
        name = "Retrofit - Type-safe HTTP client for Android and Java by Square",
        url = "https://github.com/square/retrofit"
    )
)