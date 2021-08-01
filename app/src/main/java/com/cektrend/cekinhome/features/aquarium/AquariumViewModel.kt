package com.cektrend.cekinhome.features.aquarium

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
class AquariumViewModel @Inject constructor(private val historyLogRepository: HistoryLogRepository) : BaseViewModel() {
    fun insertHistoryLog(historyLog: HistoryLog) {
        historyLogRepository.insertData(historyLog)
    }
}