package com.cektrend.cekinhome.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
@Parcelize
@Entity(tableName = "history_log_table")
data class HistoryLog(
    @ColumnInfo(name = "device_name") val deviceName: String,
    @ColumnInfo(name = "condition_state") val conditionState: String,
    @ColumnInfo(name = "date_info") val dateInfo: Long,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
) : Parcelable