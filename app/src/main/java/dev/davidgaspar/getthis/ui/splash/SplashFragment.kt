package dev.davidgaspar.getthis.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.davidgaspar.getthis.R

class SplashFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.splash_fragment, container, false)

        view.setOnClickListener {
            findNavController().navigate(R.id.action_splash_fragment_to_home_fragment)
        }

        return view
    }
}