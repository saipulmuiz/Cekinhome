package com.cektrend.cekinhome.features.aquarium

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
class AquariumFragment : BaseFragment(FragmentAquariumBinding, AquariumViewModel), View.OnClickListener, FABProgressListener, CompoundButton.OnCheckedChangeListener {
    private lateinit var dbCekinhome: DatabaseReference
    private var _binding: FragmentAquariumBinding? = null
    private val binding get() = _binding!!
    private val device: MutableList<Device> = mutableListOf()
    private var appDatabase: AppDatabase? = null
    private val compositeDisposable = CompositeDisposable()
    private var afterLaunch: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAquariumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbCekinhome = FirebaseDatabase.getInstance().reference
        appDatabase = this.context?.let { AppDatabase.getInstance(it) }
        binding.fabProgressCircle.attachListener(this)
        binding.fabFeed.setOnClickListener(this)
        binding.switchAquaLamp.setOnCheckedChangeListener(this)
        binding.switchPump.setOnCheckedChangeListener(this)
        loadStateDevice()
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
                            // addHistoryLog(HistoryLog("Lampu Aquarium", "Hidup", Date().time))
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
                            // addHistoryLog(HistoryLog("Lampu Aquarium", "Mati", Date().time))
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
                            // addHistoryLog(HistoryLog("Pompa Aquarium", "Hidup", Date().time))
                            binding.cvPump.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_view_active))
                            binding.tvIndicatorPump.text = getString(R.string.state_on)
                            binding.tvIndicatorPump.setTextColor(Color.WHITE)
                            binding.tvPump.setTextColor(Color.WHITE)
                            afterLaunch = true
                        }
                        .addOnFailureListener {
                            context?.showToast("Proses gagal, Ada kesalahan!")
                        }
                } else {
                    dbCekinhome.child("device").child("aquarium").child("pump").setValue(0)
                        .addOnSuccessListener {
                            // addHistoryLog(HistoryLog("Pompa Aquarium", "Mati", Date().time))
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

    // private fun addHistoryLog(historyLog: HistoryLog) {
    //     if (afterLaunch) {
    //         compositeDisposable.add(Observable.fromCallable { appDatabase?.historyLogDao()?.addHistoryLog(historyLog) }
    //             .subscribeOn(Schedulers.computation())
    //             .observeOn(AndroidSchedulers.mainThread())
    //             .subscribe())
    //     }
    // }

    override fun onFABProgressAnimationEnd() {
        dbCekinhome.child("device").child("aquarium").child("feed").setValue(1)
            .addOnSuccessListener {
                binding.fabFeed.isEnabled = true
                // addHistoryLog(HistoryLog("Pakan Aquarium", "Sukses", Date().time))
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

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyInstance()
        compositeDisposable.dispose()
    }
}