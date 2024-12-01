package com.logotet.bookapp.android.core.presentation

import android.app.Application
import com.logotet.bookapp.android.di.appModule
import com.logotet.bookapp.android.di.databaseModule
import com.logotet.bookapp.android.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class BookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BookApplication)
            modules(appModule, networkModule, databaseModule)
        }
    }
}