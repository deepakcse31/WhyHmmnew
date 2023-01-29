package com.example.whyhmm.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.whyhmm.R
import com.example.whyhmm.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding :ActivityMainBinding ?=null
    private lateinit var navController : NavController
    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setupViews()
        setContentView(binding?.root)
    }
    private fun setupViews() {
        try {
            bottomNavigationView = binding?.bottomNavView!!
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
            navController = navHostFragment?.navController!!
            NavigationUI.setupWithNavController(bottomNavigationView, navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homefragment->showBottomNavigation()
                    R.id.coursesfragment->showBottomNavigation()
                    R.id.activityfragment->showBottomNavigation()
                    R.id.walletfragment->showBottomNavigation()
                    R.id.supportfragment->showBottomNavigation()
                    else -> {
                        hideBottomNavigation()
                        //showBottomNavigation()
                    }
                }
            }

//            bottomNavigationView.setOnItemReselectedListener {
//                when(it.itemId) {
//                    R.id.vendorHomeDashboard -> WalkthroughFragment()
//                    R.id.loginFragment -> LoginFragment()
//                }
//            }

        }catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun showBottomNavigation() {
        binding?.apply {
            bottomNavView.visibility = View.VISIBLE
        }
    }

    private fun hideBottomNavigation() {
        binding?.apply {
            bottomNavView.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}