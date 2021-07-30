package com.cektrend.cekinhome.data.db.dao

import androidx.room.*
import com.cektrend.cekinhome.data.db.entity.HistoryLog
import io.reactivex.Flowable

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
@Dao
interface HistoryLogDao {
    @Query("SELECT * FROM history_log_table")
    fun getAll(): Flowable<List<HistoryLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyLog: HistoryLog)
}