package com.hardik.demoapicalling

import android.app.Application
import com.hardik.demoapicalling.di.AppModule
import com.hardik.demoapicalling.di.AppModuleImpl
//import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
class MyApplication: Application(){
    companion object{
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}