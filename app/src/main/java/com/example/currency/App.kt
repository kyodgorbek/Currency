package com.example.currency

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.res.Configuration
import timber.log.Timber


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }
}