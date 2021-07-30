package com.cektrend.cekinhome

import android.content.Context
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
class HistoryTable(context: Context) {
    val mContext : Context = context;

    var no: Int? = 1
    var deviceName: String? = null
    var kondisi: String? = null
    var date: String? = null

    var headerNo : String? = null
    var headerDeviceName : String? = null
    var headerKondisi : String? = null
    var headerDate : String? = null

    fun addHeader(headerNo : String, headerDeviceName: String, headerKondisi: String, headerDate : String) : HistoryTable{
        val data = HistoryTable(mContext)
        data.headerNo = headerNo
        data.headerDeviceName = headerDeviceName
        data.headerKondisi = headerKondisi
        data.headerDate = headerDate
        return data
    }

    fun addData(no : Int, deviceName: String, kondisi: String, date : String) : HistoryTable{
        val data = HistoryTable(mContext)
        data.no = no
        data.deviceName = deviceName
        data.kondisi = kondisi
        data.date = date
        return data
    }
}