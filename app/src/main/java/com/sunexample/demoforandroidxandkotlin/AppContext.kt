package com.sunexample.demoforandroidxandkotlin

import android.app.Application
import android.content.Context
import org.litepal.LitePal

class AppContext : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this

        LitePal.initialize(this)
    }

    companion object {
        private const val TAG = "AppContext"
        var appContext: Context? = null
    }
}