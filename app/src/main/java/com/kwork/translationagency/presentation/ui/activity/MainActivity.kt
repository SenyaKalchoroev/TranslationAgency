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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding.chipNavBar.setItemSelected(R.id.homeFragment,true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_host, HomeFragment())
            .commit()
        bottomMenu()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_host) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun bottomMenu() {
        binding.chipNavBar.setOnItemSelectedListener { itemId ->
            val fragment: Fragment = when (itemId) {
                R.id.homeFragment -> HomeFragment()
                R.id.ordersFragment -> OrdersFragment()
                R.id.clientsFragment -> ClientsFragment()
                R.id.taskFragment -> TaskFragment()
                R.id.settingsFragment -> SettingsFragment()
                else -> HomeFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_host, fragment)
                .commit()
        }
    }
}
