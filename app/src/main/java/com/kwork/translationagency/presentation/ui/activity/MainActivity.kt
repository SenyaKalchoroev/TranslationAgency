package com.kwork.translationagency.presentation.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kwork.translationagency.R
import com.kwork.translationagency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavController

    private val hideBottomNavDestinations = setOf(
        R.id.loginFragment,
        R.id.newOrderFragment,
        R.id.notificationFragment,
        R.id.translatorsFragment,
        R.id.chatFragment,
        R.id.profileFragment,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupNavController()
        setupBottomNavigation()
        setupDestinationListener()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_host) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupBottomNavigation() {
        binding.chipNavBar.setItemSelected(R.id.homeFragment, true)

        binding.chipNavBar.setOnItemSelectedListener { itemId ->
            when (itemId) {
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                R.id.ordersFragment -> navController.navigate(R.id.ordersFragment)
                R.id.clientsFragment -> navController.navigate(R.id.clientsFragment)
                R.id.taskFragment -> navController.navigate(R.id.taskFragment)
                R.id.settingsFragment -> navController.navigate(R.id.settingsFragment)
            }
        }
    }

    private fun setupDestinationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in hideBottomNavDestinations) {
                binding.chipNavBar.visibility = View.GONE
            } else {
                binding.chipNavBar.visibility = View.VISIBLE
            }
        }
    }

}