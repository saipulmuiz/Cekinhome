package com.cektrend.cekinhome

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pin.*


class PinActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        initiViews()
    }

    private fun initiViews() {
        t9_key_0.setOnClickListener(this)
        t9_key_1.setOnClickListener(this)
        t9_key_2.setOnClickListener(this)
        t9_key_3.setOnClickListener(this)
        t9_key_4.setOnClickListener(this)
        t9_key_5.setOnClickListener(this)
        t9_key_6.setOnClickListener(this)
        t9_key_7.setOnClickListener(this)
        t9_key_8.setOnClickListener(this)
        t9_key_9.setOnClickListener(this)
        t9_key_backspace.setOnClickListener(this)
        t9_key_fingerprint.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val editable: Editable = et_pin_field.text
        val charCount = editable.length
        if (v.tag != null && "number_button" == v.tag) {
            et_pin_field.append((v as TextView).text)
            if (charCount >= 5) {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("EXTRA_MESSAGE", "test")
                }
                startActivity(intent)
            }
            return
        } else if (v.id == R.id.t9_key_backspace) {
            if (charCount > 0) {
                editable.delete(charCount - 1, charCount)
            }
        }
    }
}