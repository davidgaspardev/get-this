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
        val downloadInfo = Gson().fromJson(downloadInfoJson, DownloadInfo::class.java)
        val viewModel = DetailViewModel(
            application,
            downloadInfo
        )

        binding.viewModel = viewModel

        return view
    }
}