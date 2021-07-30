package com.cektrend.cekinhome
/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
interface OnDayNightStateChanged {

    fun onDayNightApplied(state: Int)

    companion object{
        const val DAY = 1
        const val NIGHT = 2
    }
}