package com.cektrend.cekinhome

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.cektrend.cekinhome.data.db.AppDatabase
import com.cektrend.cekinhome.data.db.entity.HistoryLog
import com.cektrend.cekinhome.databinding.ActivityHistoryBinding
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val compositeDisposable = CompositeDisposable()
    private var appDatabase: AppDatabase? = null
    private val historys: ArrayList<HistoryLog> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.historyToolbar)
        supportActionBar?.title = "Log Histori"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appDatabase = AppDatabase.getInstance(this)
        // getAllDataFromDb()
        // val history = HistoryTable(this)
        // val data = ArrayList<HistoryTable>()
        // data.clear()
        // data.add(history.addHeader("No", "Nama Device", "Kondisi", "Tanggal"))
        // data.add(history.addData(1, "Aqua Lamp", "Hidup", "22-3-2020"))
        // data.add(history.addData(2, "Aqua Lamp", "Mati", "22-3-2020"))
        // data.add(history.addData(3, "Aqua Pump", "Hidup", "22-3-2020"))
        // data.add(history.addData(3, "Aqua Pump", "Hidup", "22-3-2020"))
        //
        // HistoryTableAdapter(this, data).also { adapter -> binding.listHistory.adapter = adapter }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    // private fun getAllDataFromDb() {
    //     compositeDisposable.add(appDatabase!!.historyLogDao().fetchAllHistoryLog()
    //         .subscribeOn(Schedulers.computation())
    //         .observeOn(AndroidSchedulers.mainThread())
    //         .subscribe { it ->
    //             historys.clear()
    //             historys.addAll(it)
    //             binding.listHistory.adapter = HistoryTableAdapter(this, historys)
    //         })
    // }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyInstance()
        compositeDisposable.dispose()
    }
}