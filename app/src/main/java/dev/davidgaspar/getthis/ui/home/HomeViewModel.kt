package dev.davidgaspar.getthis.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import dev.davidgaspar.getthis.data.config.radioButtonDownloadInfoMap
import dev.davidgaspar.getthis.data.model.DownloadInfo
import dev.davidgaspar.getthis.data.workers.DownloadWorker
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
): AndroidViewModel(application) {

    private val _selectedRadioButtonId = MutableLiveData<Int?>()
    private val selectedRadioButtonId: LiveData<Int?> get() = _selectedRadioButtonId

    fun setSelectedRadioButtonId(id: Int) {
        _selectedRadioButtonId.value = id
    }

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> get() = _toastMessage

    private fun setToastMessage(message: String) {
        _toastMessage.value = message
    }

    fun onClickLoadingButton() {
        selectedRadioButtonId.value.let { radioButtonId ->
            if (radioButtonId == null) {
                return setToastMessage("Please select a radio button")
            }

            radioButtonDownloadInfoMap[radioButtonId].let { downloadInfo ->
                if (downloadInfo == null) {
                    return setToastMessage("No download info found for selected radio button")
                }

                setToastMessage("Loading ${downloadInfo.url}")

                startDownloadInBackground(downloadInfo)
            }
        }
    }

    private fun startDownloadInBackground(downloadInfo: DownloadInfo) {
        viewModelScope.launch {
            val workManager = WorkManager.getInstance(getApplication())
            workManager.cancelAllWorkByTag(downloadInfo.url)

            try {
                val gson = Gson()
                val downloadInfoJson = gson.toJson(downloadInfo)

                val inputData = Data.Builder()
                    .putString("downloadInfoJson", downloadInfoJson)
                    .build()

                val request = OneTimeWorkRequestBuilder<DownloadWorker>()
                    .addTag(downloadInfo.url)
                    .setInputData(inputData)
                    .build()

                workManager.enqueue(request)
                setToastMessage("Downloaded ${downloadInfo.url}")
            } catch (e: Exception) {
                setToastMessage("Failed to download ${downloadInfo.url}")
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}