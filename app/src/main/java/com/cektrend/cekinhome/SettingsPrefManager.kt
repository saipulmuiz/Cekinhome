package com.cektrend.cekinhome

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
class SettingsPrefManager(_context: Context) {
    private var pref: SharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private var editor: Editor = pref.edit()

    fun setDarkMode(isFirstTime: Boolean) {
        editor.putBoolean(IS_NIGHT_MODE, isFirstTime)
        editor.commit()
    }

    fun setBiometricDefault(isBiometric: Boolean) {
        editor.putBoolean(IS_BIOMETRIC_DEFAULT, isBiometric)
        editor.commit()
    }

    val isNightMode: Boolean get() = pref.getBoolean(IS_NIGHT_MODE, false)
    val isBiometricDefault: Boolean get() = pref.getBoolean(IS_BIOMETRIC_DEFAULT, false)

    companion object {
        private const val PREF_NAME = "cekinhome-settings-pref"
        private const val IS_NIGHT_MODE = "IsNightMode"
        private const val IS_BIOMETRIC_DEFAULT = "IsBiometricDefault"
    }

}