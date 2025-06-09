package dev.davidgaspar.getthis.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import dev.davidgaspar.getthis.data.model.DownloadInfo
import dev.davidgaspar.getthis.databinding.DetailsActivityBinding

class DetailsActivity: AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        val binding = DetailsActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val downloadInfoJson = intent.getStringExtra("downloadInfoJson")
        if (downloadInfoJson == null) {
            // Log an error and return early if the argument is missing
            Log.e(TAG, "Missing 'downloadInfoJson' argument")
            finish()
            return
        }
        val downloadInfo = Gson().fromJson(downloadInfoJson, DownloadInfo::class.java)
        val viewModel = DetailsViewModel(
            application,
            downloadInfo
        )
        binding.viewModel = viewModel
    }

    companion object {
        private const val TAG = "DetailFragment"
    }
}