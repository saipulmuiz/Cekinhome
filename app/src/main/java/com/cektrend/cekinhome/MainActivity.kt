package com.cektrend.cekinhome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customBottomBar.inflateMenu(R.menu.bottom_menu)
        // setUpNavigation()
        // setBottomNavigation()
    }

    private fun setUpNavigation() {
        val navController = findNavController(R.id.main_nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        customBottomBar.setupWithNavController(navController)
    }

    private fun setBottomNavigation() {
        customBottomBar.setOnNavigationItemSelectedListener(onBottomNavListener)
    }

    private val onBottomNavListener = BottomNavigationView.OnNavigationItemSelectedListener { i ->
        var selectedFr: Fragment = DashboardFragment()

        when (i.itemId) {
            R.id.dashboardFragment -> {
                selectedFr = DashboardFragment()
            }
            R.id.settingFragment -> {
                selectedFr = SettingsFragment()
            }
        }
        val fr = supportFragmentManager.beginTransaction()
        fr.replace(R.id.main_nav_host_fragment, selectedFr)
        fr.addToBackStack(null).commit()
        true
    }
}