package com.hardik.demoapicalling

import android.app.Application
import com.hardik.demoapicalling.di.AppModule
import com.hardik.demoapicalling.di.AppModuleImpl

class MyApp: Application() {
    companion object{
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}