package com.cektrend.cekinhome

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.cektrend.cekinhome.databinding.ActivityPinBinding
import com.cektrend.cekinhome.utils.hide
import com.cektrend.cekinhome.utils.show
import com.cektrend.cekinhome.utils.showToast
import java.util.concurrent.Executors


class PinActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (DarkModePrefManager(this).isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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
        biometricPrompt.authenticate(promptInfo)
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