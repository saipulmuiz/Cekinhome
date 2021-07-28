package com.cektrend.cekinhome

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import com.cektrend.cekinhome.data.Aquarium
import com.cektrend.cekinhome.data.Device
import com.cektrend.cekinhome.databinding.FragmentAquariumBinding
import com.cektrend.cekinhome.utils.showToast
import com.github.jorgecastilloprz.listeners.FABProgressListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*

class AquariumFragment : Fragment(), View.OnClickListener, FABProgressListener, CompoundButton.OnCheckedChangeListener {
    private lateinit var dbCekinhome: DatabaseReference
    private var _binding: FragmentAquariumBinding? = null
    private val binding get() = _binding!!
    private val device: MutableList<Device> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAquariumBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbCekinhome = FirebaseDatabase.getInstance().reference
        loadStateDevice()
        binding.fabProgressCircle.attachListener(this)
        binding.fabFeed.setOnClickListener(this)
        binding.switchAquaLamp.setOnCheckedChangeListener(this)
        binding.switchPump.setOnCheckedChangeListener(this)
    }

    private fun loadStateDevice() {
        dbCekinhome.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                println(error.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.mapNotNullTo(device) { it.getValue(Device::class.java) }
                val aquarium = device[0].aquarium
                loadState(aquarium)
                if (aquarium?.lamp == 1) binding.switchAquaLamp.isChecked = true
                if (aquarium?.pump == 1) binding.switchPump.isChecked = true
                println(device[0].aquarium)
            }
        })
    }

    private fun loadState(aquarium: Aquarium?) {
        if (aquarium?.lamp == 1) {
            binding.switchAquaLamp.isChecked = true
        }
        if (aquarium?.pump == 1) {
            binding.switchPump.isChecked = true
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_feed -> {
                binding.fabProgressCircle.show()
                binding.fabFeed.isEnabled = false
                binding.fabProgressCircle.beginFinalAnimation()
            }
        }
    }

    override fun onCheckedChanged(compoundButton: CompoundButton, state: Boolean) {
        when (compoundButton.id) {
            R.id.switch_aqua_lamp -> {
                if (state) {
                    dbCekinhome.child("device").child("aquarium").child("lamp").setValue(1)
                        .addOnSuccessListener {
                            binding.cvLamp.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_view_active))
                            binding.tvIndicatorLamp.text = getString(R.string.state_on)
                            binding.tvIndicatorLamp.setTextColor(Color.WHITE)
                            binding.tvLamp.setTextColor(Color.WHITE)
                        }
                        .addOnFailureListener {
                            context?.showToast("Proses gagal, Ada kesalahan!")
                        }
                } else {
                    dbCekinhome.child("device").child("aquarium").child("lamp").setValue(0)
                        .addOnSuccessListener {
                            binding.cvLamp.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_view_default))
                            binding.tvIndicatorLamp.text = getString(R.string.state_off)
                            binding.tvIndicatorLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.card_view_text_default))
                            binding.tvLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.card_view_text_default))
                        }
                        .addOnFailureListener {
                            context?.showToast("Proses gagal, Ada kesalahan!")
                        }
                }
            }
            R.id.switch_pump -> {
                if (state) {
                    dbCekinhome.child("device").child("aquarium").child("pump").setValue(1)
                        .addOnSuccessListener {
                            binding.cvPump.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_view_active))
                            binding.tvIndicatorPump.text = getString(R.string.state_on)
                            binding.tvIndicatorPump.setTextColor(Color.WHITE)
                            binding.tvPump.setTextColor(Color.WHITE)
                        }
                        .addOnFailureListener {
                            context?.showToast("Proses gagal, Ada kesalahan!")
                        }
                } else {
                    dbCekinhome.child("device").child("aquarium").child("pump").setValue(0)
                        .addOnSuccessListener {
                            binding.cvPump.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_view_default))
                            binding.tvIndicatorPump.text = getString(R.string.state_off)
                            binding.tvIndicatorPump.setTextColor(ContextCompat.getColor(requireContext(), R.color.card_view_text_default))
                            binding.tvPump.setTextColor(ContextCompat.getColor(requireContext(), R.color.card_view_text_default))
                        }
                        .addOnFailureListener {
                            context?.showToast("Proses gagal, Ada kesalahan!")
                        }
                }
            }
        }
    }

    override fun onFABProgressAnimationEnd() {
        dbCekinhome.child("device").child("aquarium").child("feed").setValue(1)
            .addOnSuccessListener {
                binding.fabFeed.isEnabled = true
                val bottomNavView: BottomNavigationView = activity?.findViewById(R.id.customBottomBar)!!
                Snackbar.make(bottomNavView, "Ikan sudah diberi pakan... :)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).apply {
                        anchorView = bottomNavView
                    }
                    .show()
            }
            .addOnFailureListener {
                context?.showToast("Proses gagal, Ada kesalahan!")
            }
    }
}