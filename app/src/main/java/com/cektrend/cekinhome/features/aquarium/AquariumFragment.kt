package com.cektrend.cekinhome.features.aquarium

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.cektrend.cekinhome.R
import com.cektrend.cekinhome.core.base.BaseFragment
import com.cektrend.cekinhome.data.entity.Aquarium
import com.cektrend.cekinhome.data.entity.Device
import com.cektrend.cekinhome.data.db.AppDatabase
import com.cektrend.cekinhome.data.db.entity.HistoryLog
import com.cektrend.cekinhome.databinding.FragmentAquariumBinding
import com.cektrend.cekinhome.core.util.showToast
import com.github.jorgecastilloprz.listeners.FABProgressListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
@AndroidEntryPoint
class AquariumFragment : BaseFragment<FragmentAquariumBinding, AquariumViewModel>(), View.OnClickListener, FABProgressListener, CompoundButton.OnCheckedChangeListener {
    private lateinit var dbCekinhome: DatabaseReference
    private val device: MutableList<Device> = mutableListOf()
    private var appDatabase: AppDatabase? = null
    private var afterLaunch: Boolean = false

    override fun getLayoutId(): Int = R.layout.fragment_aquarium
    override fun getViewModelBindingVariable(): Int = NO_VIEW_MODEL_BINDING_VARIABLE
    private val viewModel: AquariumViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbCekinhome = FirebaseDatabase.getInstance().reference
        appDatabase = this.context?.let { AppDatabase.getInstance(it) }
        getDataBinding().apply {
            fabProgressCircle.attachListener(this@AquariumFragment)
            fabFeed.setOnClickListener(this@AquariumFragment)
            switchAquaLamp.setOnCheckedChangeListener(this@AquariumFragment)
            switchPump.setOnCheckedChangeListener(this@AquariumFragment)
        }
        loadStateDevice()
    }

    private fun loadStateDevice() {
        dbCekinhome.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                activity?.showToast("Error : $error")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.mapNotNullTo(device) { it.getValue(Device::class.java) }
                val aquarium = device[0].aquarium
                loadState(aquarium)
                println(device[0].aquarium)
            }
        })
    }

    private fun loadState(aquarium: Aquarium?) {
        if (aquarium?.lamp == 1) {
            getDataBinding().switchAquaLamp.isChecked = true
        }
        if (aquarium?.pump == 1) {
            getDataBinding().switchPump.isChecked = true
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_feed -> {
                getDataBinding().apply {
                    fabProgressCircle.show()
                    fabFeed.isEnabled = false
                    fabProgressCircle.beginFinalAnimation()
                }
            }
        }
    }

    override fun onCheckedChanged(compoundButton: CompoundButton, state: Boolean) {
        getDataBinding().apply {
            when (compoundButton.id) {
                R.id.switch_aqua_lamp -> {
                    if (state) {
                        dbCekinhome.child("device").child("aquarium").child("lamp").setValue(1)
                            .addOnSuccessListener {
                                insertHistoryLog(HistoryLog("Lampu Aquarium", "Hidup", Date().time))
                                cvLamp.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_view_active))
                                tvIndicatorLamp.text = getString(R.string.state_on)
                                tvIndicatorLamp.setTextColor(Color.WHITE)
                                tvLamp.setTextColor(Color.WHITE)
                            }
                            .addOnFailureListener {
                                context?.showToast("Proses gagal, Ada kesalahan!")
                            }
                    } else {
                        dbCekinhome.child("device").child("aquarium").child("lamp").setValue(0)
                            .addOnSuccessListener {
                                insertHistoryLog(HistoryLog("Lampu Aquarium", "Mati", Date().time))
                                cvLamp.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_view_default))
                                tvIndicatorLamp.text = getString(R.string.state_off)
                                tvIndicatorLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.card_view_text_default))
                                tvLamp.setTextColor(ContextCompat.getColor(requireContext(), R.color.card_view_text_default))
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
                                insertHistoryLog(HistoryLog("Pompa Aquarium", "Hidup", Date().time))
                                cvPump.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_view_active))
                                tvIndicatorPump.text = getString(R.string.state_on)
                                tvIndicatorPump.setTextColor(Color.WHITE)
                                tvPump.setTextColor(Color.WHITE)
                                afterLaunch = true
                            }
                            .addOnFailureListener {
                                context?.showToast("Proses gagal, Ada kesalahan!")
                            }
                    } else {
                        dbCekinhome.child("device").child("aquarium").child("pump").setValue(0)
                            .addOnSuccessListener {
                                insertHistoryLog(HistoryLog("Pompa Aquarium", "Mati", Date().time))
                                cvPump.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_view_default))
                                tvIndicatorPump.text = getString(R.string.state_off)
                                tvIndicatorPump.setTextColor(ContextCompat.getColor(requireContext(), R.color.card_view_text_default))
                                tvPump.setTextColor(ContextCompat.getColor(requireContext(), R.color.card_view_text_default))
                            }
                            .addOnFailureListener {
                                context?.showToast("Proses gagal, Ada kesalahan!")
                            }
                    }
                }
            }
        }
    }

    private fun insertHistoryLog(historyLog: HistoryLog) {
        if (afterLaunch) {
            viewModel.insertHistoryLog(historyLog)
        }
    }

    override fun onFABProgressAnimationEnd() {
        dbCekinhome.child("device").child("aquarium").child("feed").setValue(1)
            .addOnSuccessListener {
                getDataBinding().fabFeed.isEnabled = true
                insertHistoryLog(HistoryLog("Pakan Aquarium", "Sukses", Date().time))
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