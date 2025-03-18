package dev.davidgaspar.getthis.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dev.davidgaspar.getthis.databinding.HomeFragmentBinding
import dev.davidgaspar.getthis.di.DependencyProvider

class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireActivity().application
        val viewModel = HomeViewModel(
            application,
            DependencyProvider.getImageRepository(application)
        )

        binding.viewModel = viewModel

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}