package dev.davidgaspar.getthis.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.davidgaspar.getthis.data.model.DownloadInfo

class DetailsViewModel(
    application: Application,
    downloadInfo: DownloadInfo,
): AndroidViewModel(application) {

    private val _downloadInfo = MutableLiveData(downloadInfo)
    val downloadInfo: LiveData<DownloadInfo> get() = _downloadInfo
}