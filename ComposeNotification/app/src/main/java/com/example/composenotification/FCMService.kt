package com.example.composenotification

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.composenotification.MyNotification.showNotificationOnStatusBar
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val FCM_TOKEN_PREF_KEY = "fcm_token"
const val LOG_TAG = "FCMService"

class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Incoming Message
        Log.d(LOG_TAG, "Message From : ${message.from}")

        // Data Payload
        if (message.data.isNotEmpty()) {
            Log.d(LOG_TAG, "Message Data : ${message.data}")
        }

        // Check if message contains a notification payload
        message.data.let {
            Log.d(LOG_TAG, "Message Data Body : ${it["body"]}")
            Log.d(LOG_TAG, "Message Data Title :  ${it["title"]}")

            //when app in foreground that notification is not shown on status bar
            //lets write a code to display notification in status bar when app in foreground.
            showNotificationOnStatusBar(applicationContext, it)
        }

        if (message.notification != null) {
            Log.d(LOG_TAG, "Notification : ${message.notification}")
            Log.d(LOG_TAG, "Notification Title : ${message.notification!!.title}")
            Log.d(LOG_TAG, "Notification Body : ${message.notification!!.body}")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        GlobalScope.launch {
            saveFCMToken(token)
        }
    }

    // we can used this Token to send it on our Server
    private suspend fun saveFCMToken(token: String) {
        Log.d(LOG_TAG, "saveFCMToken : $token")
        val gckTokenKey = stringPreferencesKey(FCM_TOKEN_PREF_KEY)
        baseContext.dataStore.edit { pref ->
            pref[gckTokenKey] = token
        }
    }
}