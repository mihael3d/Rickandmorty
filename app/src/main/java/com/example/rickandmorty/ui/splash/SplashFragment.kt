package com.example.rickandmorty.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val authResult = findNavController().currentBackStackEntry
//            ?.savedStateHandle
//            ?.remove<Boolean>(FinishAuthFragment.AUTH_FLOW_RESULT_KEY) == true

//        if (authResult) {
            navigateToMainScreen()
//            return
//        }

        // Navigate with SingleLiveEvent from Splash screen
//        splashViewModel.splashNavCommand.observe(viewLifecycleOwner, Observer { splashNavCommand ->
//            when (splashNavCommand) {
//                SplashNavCommand.NAVIGATE_TO_MAIN -> navigateToMainScreen()
//                SplashNavCommand.NAVIGATE_TO_AUTH -> navigateToAuthFlow()
//                null -> {
//                    // do nothing
//                }
//            }
//        })
    }

    private fun navigateToAuthFlow() {
//        findNavController().navigate(R.id.action__SplashFragment__to__AuthFlow, StartAuthFragmentArgs(isFromSplashScreen = true).toBundle())
    }

    private fun navigateToMainScreen() {
        findNavController().navigate(R.id.action__SplashFragment__to__MainFragment)
    }
}