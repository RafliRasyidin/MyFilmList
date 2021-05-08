package com.rasyidin.myfilmlist

import android.app.Application
import com.rasyidin.myfilmlist.core.di.networkModule
import com.rasyidin.myfilmlist.core.di.repositoryModule
import com.rasyidin.myfilmlist.di.useCaseModule
import com.rasyidin.myfilmlist.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ObsoleteCoroutinesApi
@FlowPreview
class MyApplication : Application() {
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    repositoryModule,
                    networkModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}