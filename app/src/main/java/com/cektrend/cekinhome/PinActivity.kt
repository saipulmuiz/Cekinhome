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
import com.cektrend.cekinhome.databinding.ActivityPinBinding
import com.cektrend.cekinhome.utils.hide
import com.cektrend.cekinhome.utils.show
import com.cektrend.cekinhome.utils.showToast


class PinActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initiViews()
    }

    private fun initiViews() {
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
        binding.t9KeyFingerprint.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val editable: Editable = binding.etPinField.text
        val charCount = editable.length
        if (v.tag != null && "number_button" == v.tag) {
            binding.etPinField.append((v as TextView).text)
            if (charCount >= 5) {
                binding.progressBar.show()
                Handler(Looper.getMainLooper()).postDelayed({
                    if (editable.toString() == "020198") {
                        val intent = Intent(this, MainActivity::class.java).apply {
                            putExtra("EXTRA_MESSAGE", "test")
                        }
                        startActivity(intent)
                        binding.progressBar.hide()
                    } else {
                        binding.progressBar.hide()
                        this.showToast("Pin salah, silahkan cobalagi..!")
                        editable.clear()
                    }
                }, 2000)
            }
            return
        } else if (v.id == R.id.t9_key_backspace) {
            if (charCount > 0) {
                editable.delete(charCount - 1, charCount)
            }
        }
    }
}