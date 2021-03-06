package com.michlindev.dariointerview

import android.app.Application
import android.content.Context

//Global application context provider
class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}


