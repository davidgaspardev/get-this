package dev.davidgaspar.getthis.ui.home

import android.app.Application
import android.util.Log
import android.widget.RadioGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

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
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}