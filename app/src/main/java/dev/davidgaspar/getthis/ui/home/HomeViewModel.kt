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
import dev.davidgaspar.getthis.data.workers.DownloadWorker
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
): AndroidViewModel(application) {

    private val _selectedRadioButtonId = MutableLiveData<Int?>()
    val selectedRadioButtonId: LiveData<Int?> get() = _selectedRadioButtonId

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
                setToastMessage("Please select a radio button")
            }

            radioButtonDownloadInfoMap[radioButtonId]?.let { downloadInfo ->
                setToastMessage("Loading ${downloadInfo.url}")

                viewModelScope.launch {
                    try {
                        val gson = Gson()
                        val downloadInfoJson = gson.toJson(downloadInfo)

                        val inputData = Data.Builder()
                            .putString("downloadInfoJson", downloadInfoJson)
                            .build()

                        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
                            .setInputData(inputData)
                            .build()

                        WorkManager.getInstance(getApplication()).enqueue(request)
                        setToastMessage("Downloaded ${downloadInfo.url}")
                    } catch (e: Exception) {
                        setToastMessage("Failed to download ${downloadInfo.url}")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}