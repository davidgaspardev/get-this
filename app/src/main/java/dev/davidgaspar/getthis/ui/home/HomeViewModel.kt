package dev.davidgaspar.getthis.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dev.davidgaspar.getthis.data.config.radioButtonUrlMap
import dev.davidgaspar.getthis.data.repository.ImageRepository
import dev.davidgaspar.getthis.data.workers.DownloadWorker
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
    private val imageRepository: ImageRepository
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

            radioButtonUrlMap[radioButtonId]?.let { url ->
                setToastMessage("Loading $url")

                viewModelScope.launch {
                    try {
                        val inputData = Data.Builder()
                            .putString("url", url)
                            .build()

                        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
                            .setInputData(inputData)
                            .build()

                        WorkManager.getInstance(getApplication()).enqueue(request)
                        setToastMessage("Downloaded $url")
                    } catch (e: Exception) {
                        setToastMessage("Failed to download $url")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}