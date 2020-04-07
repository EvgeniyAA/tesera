package com.gemini.base

import android.app.Application
import com.facebook.stetho.BuildConfig
import timber.log.Timber
import toothpick.ktp.KTP
import timber.log.Timber.DebugTree
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import core.di.DI
import toothpick.Toothpick
import toothpick.configuration.Configuration


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Toothpick.setConfiguration(Configuration.forDevelopment())
        initStetho()
        initTimber()
        initThreetenABP()
    }

    private fun initThreetenABP() {
        AndroidThreeTen.init(this)
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