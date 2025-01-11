package dev.davidgaspar.getthis.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.davidgaspar.getthis.R
import dev.davidgaspar.getthis.databinding.SplashFragmentBinding

class SplashFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SplashFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = SplashViewModel(requireActivity().application)
        val view = binding.root

        binding.viewModel?.booted?.observe(viewLifecycleOwner) { booted ->
            if (booted) {
                findNavController().navigate(R.id.action_splash_fragment_to_home_fragment)
            }
        }

        return view
    }
}