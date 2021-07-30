package com.cektrend.cekinhome.features.bedroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cektrend.cekinhome.data.entity.Device
import com.cektrend.cekinhome.databinding.FragmentBedroomBinding
import com.google.firebase.database.*
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
class BedroomFragment : Fragment() {
    private lateinit var dbCekinhome: DatabaseReference
    private var _binding: FragmentBedroomBinding? = null
    private val binding get() = _binding!!
    private val device: MutableList<Device> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBedroomBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbCekinhome = FirebaseDatabase.getInstance().reference
    }
}