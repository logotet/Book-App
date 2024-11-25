package com.logotet.bookapp.android

import android.app.Application
import com.logotet.bookapp.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookApplication)
            modules(appModule)
        }
    }
}