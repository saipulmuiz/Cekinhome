package com.cektrend.cekinhome.features.history

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.cektrend.cekinhome.R
import com.cektrend.cekinhome.core.base.BaseActivity
import com.cektrend.cekinhome.data.db.AppDatabase
import com.cektrend.cekinhome.data.db.entity.HistoryLog
import com.cektrend.cekinhome.databinding.ActivityHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
@AndroidEntryPoint
class HistoryActivity : BaseActivity<ActivityHistoryBinding, HistoryViewModel>() {
    private var appDatabase: AppDatabase? = null

    override fun getLayoutId(): Int = R.layout.activity_history
    override fun getViewModelBindingVariable(): Int = NO_VIEW_MODEL_BINDING_VARIABLE
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryTableAdapter
    private val historys: ArrayList<HistoryLog> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(getDataBinding().historyToolbar)
        supportActionBar?.title = "Log Histori"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appDatabase = AppDatabase.getInstance(this)
        adapter = HistoryTableAdapter(this, historys)
        getDataBinding().listHistory.adapter = adapter
        viewModel.getHistoryLogs().observe(this, {
            historys.clear()
            historys.addAll(it)
            adapter.notifyDataSetChanged()
        })
        viewModel.loadHistoryLog()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHistoryLog()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}