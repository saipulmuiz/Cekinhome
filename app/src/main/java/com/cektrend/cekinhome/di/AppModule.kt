package com.cektrend.cekinhome.di

import android.content.Context
import androidx.room.Room
import com.cektrend.cekinhome.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Saipul Muiz on 7/30/2021.
 * Cekinhome | Made with love
 * Check our website -> Cektrend Studio | https://cektrend.com for more information
 * For question and project collaboration contact me to saipulmuiz87@gmail.com
 */
@InstallIn(SingletonComponent::class)
@Module
class AppModule{
    @Provides
    @DbName
    fun provideDbName() = "cekinhome.db"


    @Provides
    fun provideStudentDatabase(@ApplicationContext context: Context, @DbName dbName: String): AppDatabase{
        return Room.databaseBuilder(context,
            AppDatabase::class.java, dbName)
            .build()
    }

    @Provides
    fun provideStudentDao(appDatabase: AppDatabase) = appDatabase.historyLogDao()

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
}