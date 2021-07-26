package com.cektrend.cekinhome

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cektrend.cekinhome.databinding.ActivityMainBinding
import com.cektrend.cekinhome.utils.showToast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private var doubleBack = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (DarkModePrefManager(this).isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.customBottomBar.inflateMenu(R.menu.bottom_menu)
        setUpNavigation()
        binding.fabInfo.setOnClickListener { this.showToast("fab button di click!") }
    }

    private fun setUpNavigation() {
        val navController = findNavController(R.id.main_nav_host_fragment)
        binding.customBottomBar.setupWithNavController(navController)
        binding.customBottomBar.background = null
        binding.customBottomBar.menu.getItem(1).isEnabled = false
        binding.customBottomBar.setOnItemSelectedListener(onBottomNavigationListener)
        loadFragment(DashboardFragment())
    }

    private val onBottomNavListener = BottomNavigationView.OnNavigationItemSelectedListener { i ->
        var selectedFr: Fragment = DashboardFragment()

        when (i.itemId) {
            R.id.action_home -> {
                selectedFr = DashboardFragment()
            }
            R.id.action_settings -> {
                selectedFr = SettingsFragment()
            }
        }
        val fr = supportFragmentManager.beginTransaction()
        fr.replace(R.id.main_nav_host_fragment, selectedFr)
        fr.addToBackStack(null).commit()
        true
    }

    private val onBottomNavigationListener = NavigationBarView.OnItemSelectedListener { i ->
        when (i.itemId) {
            R.id.action_home -> {
                loadFragment(DashboardFragment())
                return@OnItemSelectedListener true
            }
            R.id.action_settings -> {
                loadFragment(SettingsFragment())
                return@OnItemSelectedListener true
            }
            else -> {
                return@OnItemSelectedListener false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.main_nav_host_fragment, fragment).addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.main_nav_host_fragment).navigateUp()

    override fun onBackPressed() {
        supportFragmentManager.apply {
            if (doubleBack) {
                finish()
                return
            }
            doubleBack = true
            this@MainActivity.showToast("Tekan sekali lagi untuk keluar..")
            Handler(Looper.getMainLooper()).postDelayed({ doubleBack = false }, 2000)
        }
    }
}