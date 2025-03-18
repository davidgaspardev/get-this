package dev.davidgaspar.getthis.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.davidgaspar.getthis.R
import dev.davidgaspar.getthis.data.repository.ImageRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
    private val imageRepository: ImageRepository
): AndroidViewModel(application) {

    private val radioButtonUrlMap = mapOf(
        R.id.radio_glide to "/bumptech/glide",
        R.id.radio_loadapp to "/udacity/nd940-c3-advanced-android-programming-project-starter",
        R.id.radio_retrofit to "/square/retrofit"
    )

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
                        imageRepository.download(url)
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