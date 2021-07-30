package com.cektrend.cekinhome.features.pin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.TextView
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.cektrend.cekinhome.BuildConfig
import com.cektrend.cekinhome.R
import com.cektrend.cekinhome.core.base.BaseActivity
import com.cektrend.cekinhome.core.util.hide
import com.cektrend.cekinhome.core.util.show
import com.cektrend.cekinhome.features.settings.SettingsPrefManager
import com.cektrend.cekinhome.databinding.ActivityPinBinding
import com.cektrend.cekinhome.core.util.showToast
import com.cektrend.cekinhome.features.main.MainActivity

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */

class PinActivity : BaseActivity<ActivityPinBinding, PinViewModel>(), View.OnClickListener {

    override fun getViewModelBindingVariable(): Int = NO_VIEW_MODEL_BINDING_VARIABLE
    override fun getLayoutId(): Int = R.layout.activity_pin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataBinding().pinActivity.setBackgroundColor(ContextCompat.getColor(this, R.color.activity_bg))
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
        getDataBinding().t9KeyFingerprint.setOnClickListener { biometricPrompt.authenticate(promptInfo) }
    }

    private fun initiViews() {
        getDataBinding().apply {
            tvVersion.text = BuildConfig.VERSION_NAME
            t9Key0.setOnClickListener(this@PinActivity)
            t9Key1.setOnClickListener(this@PinActivity)
            t9Key2.setOnClickListener(this@PinActivity)
            t9Key3.setOnClickListener(this@PinActivity)
            t9Key4.setOnClickListener(this@PinActivity)
            t9Key5.setOnClickListener(this@PinActivity)
            t9Key6.setOnClickListener(this@PinActivity)
            t9Key7.setOnClickListener(this@PinActivity)
            t9Key8.setOnClickListener(this@PinActivity)
            t9Key9.setOnClickListener(this@PinActivity)
            t9KeyBackspace.setOnClickListener(this@PinActivity)
        }
    }

    override fun onClick(v: View) {
        val editable: Editable = getDataBinding().etPinField.text
        val charCount = editable.length
        if (v.tag != null && "number_button" == v.tag) {
            getDataBinding().etPinField.append((v as TextView).text)
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
        getDataBinding().progressBar.show()
        Handler(Looper.getMainLooper()).postDelayed({
            if (state) {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("EXTRA_MESSAGE", "test")
                }
                startActivity(intent)
                getDataBinding().progressBar.hide()
            } else {
                getDataBinding().progressBar.hide()
                this.showToast("Pin salah, silahkan cobalagi..!")
            }
        }, 2000)
    }
}