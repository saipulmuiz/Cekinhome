package com.cektrend.cekinhome

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.cektrend.cekinhome.databinding.ActivityPinBinding
import com.cektrend.cekinhome.core.util.hide
import com.cektrend.cekinhome.core.util.show
import com.cektrend.cekinhome.core.util.showToast
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */

class PinActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.pinActivity.setBackgroundColor(ContextCompat.getColor(this, R.color.activity_bg))
        initiViews()
        initBiometric()
    }

    private fun initBiometric() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        // user clicked negative button
                    } else {
                        return
                    }
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    authPin(true)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("Sidik jari tidak cocok!")
                }
            })
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Masuk ke aplikasi Cekinhome")
            .setSubtitle("Gunakan biometrik yang terdaftar.")
            .setNegativeButtonText("Gunakan Pin")
            .build()
        if (SettingsPrefManager(this).isBiometricDefault) {
            biometricPrompt.authenticate(promptInfo)
        }
        binding.t9KeyFingerprint.setOnClickListener { biometricPrompt.authenticate(promptInfo) }
    }

    private fun initiViews() {
        binding.tvVersion.text = BuildConfig.VERSION_NAME
        binding.t9Key0.setOnClickListener(this)
        binding.t9Key1.setOnClickListener(this)
        binding.t9Key2.setOnClickListener(this)
        binding.t9Key3.setOnClickListener(this)
        binding.t9Key4.setOnClickListener(this)
        binding.t9Key5.setOnClickListener(this)
        binding.t9Key6.setOnClickListener(this)
        binding.t9Key7.setOnClickListener(this)
        binding.t9Key8.setOnClickListener(this)
        binding.t9Key9.setOnClickListener(this)
        binding.t9KeyBackspace.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val editable: Editable = binding.etPinField.text
        val charCount = editable.length
        if (v.tag != null && "number_button" == v.tag) {
            binding.etPinField.append((v as TextView).text)
            if (charCount >= 5) {
                if (editable.toString() == "020198") {
                    authPin(true)
                } else {
                    authPin(false)
                    editable.clear()
                }
            }
            return
        } else if (v.id == R.id.t9_key_backspace) {
            if (charCount > 0) {
                editable.delete(charCount - 1, charCount)
            }
        }
    }

    private fun authPin(state: Boolean) {
        binding.progressBar.show()
        Handler(Looper.getMainLooper()).postDelayed({
            if (state) {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("EXTRA_MESSAGE", "test")
                }
                startActivity(intent)
                binding.progressBar.hide()
            } else {
                binding.progressBar.hide()
                this.showToast("Pin salah, silahkan cobalagi..!")
            }
        }, 2000)
    }
}