package com.cektrend.cekinhome.features.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cektrend.cekinhome.*
import com.cektrend.cekinhome.core.util.OnDayNightStateChanged
import com.cektrend.cekinhome.databinding.ActivityMainBinding
import com.cektrend.cekinhome.core.util.showToast
import com.cektrend.cekinhome.features.DashboardFragment
import com.cektrend.cekinhome.features.history.HistoryActivity
import com.cektrend.cekinhome.features.settings.SettingsFragment
import com.cektrend.cekinhome.features.settings.SettingsPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var doubleBack = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (SettingsPrefManager(this).isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainActivity.setBackgroundColor(ContextCompat.getColor(this, R.color.activity_bg))
        binding.customBottomBar.inflateMenu(R.menu.bottom_menu)
        setUpNavigation()
        binding.fabInfo.setOnClickListener { startActivity(Intent(this, HistoryActivity::class.java)) }
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            applyDayNight(OnDayNightStateChanged.DAY)
        } else {
            applyDayNight(OnDayNightStateChanged.NIGHT)
        }
    }

    private fun applyDayNight(state: Int) {
        if (state == OnDayNightStateChanged.DAY) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.blue_primary_dark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.mainActivity.setBackgroundColor(ContextCompat.getColor(this, R.color.activity_bg))
            binding.bottomAppBar.backgroundTint = ContextCompat.getColorStateList(this, R.color.white)
            binding.customBottomBar.itemIconTintList = ContextCompat.getColorStateList(this, R.color.dark_gray)
            binding.customBottomBar.itemTextColor = ContextCompat.getColorStateList(this, R.color.dark_gray)
        } else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.dark_gray)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.mainActivity.setBackgroundColor(ContextCompat.getColor(this, R.color.activity_bg))
            binding.bottomAppBar.backgroundTint = ContextCompat.getColorStateList(this, R.color.dark_gray)
            binding.customBottomBar.itemIconTintList = ContextCompat.getColorStateList(this, R.color.white)
            binding.customBottomBar.itemTextColor = ContextCompat.getColorStateList(this, R.color.white)
        }
    }
}