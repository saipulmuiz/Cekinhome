package com.cektrend.cekinhome.features.history

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.cektrend.cekinhome.core.base.BaseViewModel
import com.cektrend.cekinhome.data.db.entity.HistoryLog
import com.cektrend.cekinhome.data.db.repository.HistoryLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyLogRepository: HistoryLogRepository) : BaseViewModel() {

    private var mTriggerFetchData = MutableLiveData<Boolean>()
    private var historyLog : LiveData<List<HistoryLog>> = Transformations.switchMap(mTriggerFetchData){
        historyLogRepository.getAllData()
    }

    fun insertHistoryLog(historyLog: HistoryLog){
        historyLogRepository.insertData(historyLog)
    }

    fun getHistoryLogs(): LiveData<List<HistoryLog>> {
        return historyLog
    }

    fun loadHistoryLog() {
        mTriggerFetchData.value = true
    }
}