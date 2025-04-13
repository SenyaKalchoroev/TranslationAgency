package com.kwork.translationagency.presentation.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.kwork.translationagency.R
import com.kwork.translationagency.databinding.ActivityMainBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.kwork.translationagency.presentation.ui.fragments.clients.ClientsFragment
import com.kwork.translationagency.presentation.ui.fragments.home.HomeFragment
import com.kwork.translationagency.presentation.ui.fragments.orders.OrdersFragment
import com.kwork.translationagency.presentation.ui.fragments.settings.SettingsFragment
import com.kwork.translationagency.presentation.ui.fragments.tasks.TaskFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavController()
        setupBottomNavigation()
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
}

