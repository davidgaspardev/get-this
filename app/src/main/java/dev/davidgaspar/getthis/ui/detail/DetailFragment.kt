package dev.davidgaspar.getthis.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dev.davidgaspar.getthis.data.model.DownloadInfo
import dev.davidgaspar.getthis.databinding.DetailFragmentBinding

class DetailFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireActivity().application
        val downloadInfoJson = arguments?.getString("downloadInfoJson")
        if (downloadInfoJson == null) {
            // Log an error and return early if the argument is missing
            android.util.Log.e("DetailFragment", "Missing 'downloadInfoJson' argument")
            requireActivity().finish() // Close the activity or handle as appropriate
            return binding.root
        }
        val downloadInfo = Gson().fromJson(downloadInfoJson, DownloadInfo::class.java)
        val viewModel = DetailViewModel(
            application,
            downloadInfo
        )

        binding.viewModel = viewModel

        return view
    }
}