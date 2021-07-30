package com.cektrend.cekinhome.core.util

import android.content.Context
import android.widget.Toast
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}