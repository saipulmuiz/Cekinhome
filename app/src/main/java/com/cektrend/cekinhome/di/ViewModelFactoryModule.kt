package com.cektrend.cekinhome.di

import androidx.lifecycle.ViewModelProvider
import com.cektrend.cekinhome.vmfactory.HistoryLogViewModelFactory

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
abstract class ViewModelFactoryModule {
    internal abstract fun bindViewModelFactory(vMFactory : HistoryLogViewModelFactory) : ViewModelProvider.Factory
}