package com.example.composenotification

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.FirebaseApp

// Create singleton instance of DataStore Preferences
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "LocalStore")

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}