package dev.davidgaspar.getthis.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SplashViewModel(application: Application): AndroidViewModel(application) {

    private val _booted = MutableLiveData<Boolean>(false)
    val booted get() = _booted

    init {
        // Simulate a boot process
        Thread {
            Thread.sleep(5000)
            _booted.postValue(true)
        }.start()
    }
}