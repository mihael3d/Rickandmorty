package com.example.rickandmorty.ui.main

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentMainBinding
import com.example.rickandmorty.extantions.setupWithNavController


/**
 * Main fragment -- container for bottom navigation
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    private var currentNavController: LiveData<NavController>? = null
    private var fragmentMainBinding : FragmentMainBinding? = null

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        safeSetupBottomNavigationBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        fragmentMainBinding = binding
        if (savedInstanceState == null) {
            safeSetupBottomNavigationBar()
        }
    }

    private fun safeSetupBottomNavigationBar() {
        // Fix for java.lang.IllegalStateException: FragmentManager is already executing transactions
        // on launching adb command with deep link
        Handler().post {
            setupBottomNavigationBar()
        }
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
                R.navigation.characters_nav_graph,
                R.navigation.locations_nav_graph,
                R.navigation.seasons_nav_graph,
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = fragmentMainBinding?.fragmentMainBottomNavigation?.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = requireActivity().supportFragmentManager,
            containerId = R.id.fragment_main__nav_host_container,
            intent = requireActivity().intent
        )

        currentNavController = controller

        // Fix for crash on app folding (will be crashed in onDestroyView of NavHostFragment, if you use 'Don't keep activities' mode).
        //
        // Caused by: java.lang.IllegalStateException:
        // View androidx.fragment.app.FragmentContainerView{1595b6 V.E...... ......ID 0,0-1080,1584 #7f080099 app:id/fragment_main__nav_host_container}
        // does not have a NavController set
        currentNavController?.observe(viewLifecycleOwner, Observer { liveDataController ->
            Navigation.setViewNavController(requireView(), liveDataController)
        })
    }

    override fun onDestroy() {
        fragmentMainBinding = null
        super.onDestroy()
    }

}