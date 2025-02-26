package com.logotet.bookapp

import android.app.Application
import com.logotet.bookapp.di.appModule
import com.logotet.bookapp.di.databaseModule
import com.logotet.bookapp.di.networkModule
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