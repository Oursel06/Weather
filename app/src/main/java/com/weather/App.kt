package com.weather

import android.app.Application
import android.provider.ContactsContract.Data

class App : Application() {
    companion object{
        lateinit var instance: App
        val database: Database by lazy {
            Database(instance)
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}