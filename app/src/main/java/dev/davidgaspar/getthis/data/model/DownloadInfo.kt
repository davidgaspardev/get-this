package dev.davidgaspar.getthis.data.model

data class DownloadInfo(
    val name: String,
    val url: String,
) {
    lateinit var status: String
    lateinit var filePath: String
}