package com.cektrend.cekinhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.cektrend.cekinhome.databinding.FragmentSettingsBinding
import com.cektrend.cekinhome.utils.showToast

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDarkModeSwitch()
    }

    private fun setDarkModeSwitch() {
        binding.darkModeSwitch.isChecked = DarkModePrefManager(requireContext()).isNightMode
        binding.darkModeSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val darkModePrefManager = DarkModePrefManager(requireContext())
            darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode)
            // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            // recreate(requireActivity())
        })
    }
}