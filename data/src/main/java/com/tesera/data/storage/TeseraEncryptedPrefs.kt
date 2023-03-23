package com.tesera.data.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TeseraEncryptedPrefs @Inject constructor(
    @ApplicationContext private val context: Context
) : TeseraPrefs {

    companion object {
        const val PREFS_KEY_AUTH_TOKEN = "prefs key auth token"
        const val PREFS_KEY_USERNAME = "prefs key username"
    }

    private val prefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "tesera_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override var authToken: String
        get() = prefs.getString(PREFS_KEY_AUTH_TOKEN, "") ?: ""
        set(value) {
            prefs.edit().putString(PREFS_KEY_AUTH_TOKEN, value).apply()
        }

    override var username: String
        get() = prefs.getString(PREFS_KEY_USERNAME, "") ?: ""
        set(value) {
            prefs.edit().putString(PREFS_KEY_USERNAME, value).apply()
        }
}