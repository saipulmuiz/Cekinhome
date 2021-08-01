package com.cektrend.cekinhome.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.cektrend.cekinhome.BuildConfig
import com.cektrend.cekinhome.core.util.OnDayNightStateChanged
import com.cektrend.cekinhome.R
import com.cektrend.cekinhome.databinding.FragmentSettingsBinding
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
class SettingsFragment : Fragment(), OnDayNightStateChanged, CompoundButton.OnCheckedChangeListener {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDarkModeSwitch()
        binding.tvAppVersion.text = BuildConfig.VERSION_NAME
    }

    private fun setDarkModeSwitch() {
        binding.darkModeSwitch.isChecked = SettingsPrefManager(requireContext()).isNightMode
        binding.biometricSwitch.isChecked = SettingsPrefManager(requireContext()).isBiometricDefault
        binding.darkModeSwitch.setOnCheckedChangeListener(this)
        binding.biometricSwitch.setOnCheckedChangeListener(this)
    }

    override fun onDayNightApplied(state: Int) {
        binding.darkModeSwitch.isChecked = state != OnDayNightStateChanged.DAY
    }

    override fun onCheckedChanged(compoundButton: CompoundButton, isChecked: Boolean) {
        val settingsPrefManager = SettingsPrefManager(requireContext())
        when (compoundButton.id) {
            R.id.darkModeSwitch -> {
                if (isChecked) {
                    settingsPrefManager.setDarkMode(true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    settingsPrefManager.setDarkMode(false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            R.id.biometric_switch -> {
                if (isChecked) {
                    settingsPrefManager.setBiometricDefault(true)
                } else {
                    settingsPrefManager.setBiometricDefault(false)
                }
            }
        }
    }
}