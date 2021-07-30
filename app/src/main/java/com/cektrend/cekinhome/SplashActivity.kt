package com.cektrend.cekinhome

import com.cektrend.cekinhome.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.cektrend.cekinhome.databinding.ActivitySplashBinding

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        if (SettingsPrefManager(this).isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.versiText.text = BuildConfig.VERSION_NAME
        initViews()
    }

    private fun initViews() {
        val fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom)
        binding.splashImage.animation = fromBottom
        binding.appName.animation = fromBottom
        binding.nameDev.animation = fromBottom
        binding.versiText.animation = fromBottom
        binding.tvSubtitleSplash.animation = fromBottom

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, PinActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}