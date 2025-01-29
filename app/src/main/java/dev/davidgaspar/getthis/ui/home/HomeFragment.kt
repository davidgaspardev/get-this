package dev.davidgaspar.getthis.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.davidgaspar.getthis.databinding.HomeFragmentBinding
import dev.davidgaspar.getthis.ui.components.LoadingButton

class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.loadingButtonContainer.addView(LoadingButton(requireContext()))

        return view
    }
}