package com.tesera.base

import android.app.Application
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initStetho()
        initTimber()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            val initializerBuilder = Stetho
                .newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build()
            Stetho.initialize(initializerBuilder)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

}