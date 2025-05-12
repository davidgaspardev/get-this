package dev.davidgaspar.getthis.data.model

data class DownloadInfo(
    val name: String,
    val url: String,
) {
    var status: String? = null
    var filePath: String? = null
}