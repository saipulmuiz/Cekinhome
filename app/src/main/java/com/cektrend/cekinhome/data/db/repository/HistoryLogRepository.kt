package com.cektrend.cekinhome.data.db.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.cektrend.cekinhome.data.db.dao.HistoryLogDao
import com.cektrend.cekinhome.data.db.entity.HistoryLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
class HistoryLogRepository @Inject constructor(historyLogDao: HistoryLogDao, compositeDisposable: CompositeDisposable) {

    var historyLogDao: HistoryLogDao = historyLogDao
    var comp: CompositeDisposable = compositeDisposable

    fun getAllData(): LiveData<List<HistoryLog>> {
        return LiveDataReactiveStreams.fromPublisher(historyLogDao.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation()))
    }

    fun insertData(historyLog: HistoryLog) {
        comp.add(Observable.fromCallable { historyLogDao.insert(historyLog) }
            .subscribeOn(Schedulers.computation())
            .subscribe())
    }
}