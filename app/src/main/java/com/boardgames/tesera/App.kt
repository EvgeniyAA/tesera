package com.boardgames.tesera

import android.app.Application
import timber.log.Timber
import toothpick.ktp.KTP
import timber.log.Timber.DebugTree
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.boardgames.tesera.di.AppModule
import com.boardgames.tesera.di.DI
import com.boardgames.tesera.di.NetworkModule
import toothpick.Toothpick
import toothpick.configuration.Configuration


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Toothpick.setConfiguration(Configuration.forDevelopment())
        openScopes()
        initStetho()
        initTimber()
        initThreetenABP()
    }

    private fun openScopes() {
        KTP.openScope(DI.APP_SCOPE).installModules(AppModule(this))
        KTP.openScope(DI.NETWORK_SCOPE).installModules(NetworkModule(BuildConfig.ENDPOINT))
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