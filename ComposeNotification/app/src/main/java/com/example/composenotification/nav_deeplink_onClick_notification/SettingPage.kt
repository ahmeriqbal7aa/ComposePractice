package com.example.composenotification.nav_deeplink_onClick_notification

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.composenotification.dataStore
import com.example.composenotification.showToast
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun SettingPage() {
    //Get LocalContext.
    val localContext = LocalContext.current
    //Create boolean preference key
    val isPushEnableKey = booleanPreferencesKey("isPushEnable")
    // create mutableState for switch checked property.
    // default we set it value false.
    // Now Read the isPushEnable value from DataStore.
    val isPushDataStoreValue = flow {
        localContext.dataStore.data.map {
            it[isPushEnableKey]
        }.collect(collector = {
            if (it != null) {
                emit(it)
            }
        })
    }.collectAsState(initial = false)

    val coroutine = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
    ) {

        // In Setting Page I will add switch composable UI element.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Text(
                text = "Enable Push Notification",
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .weight(1f)
            )
            Switch(checked = isPushDataStoreValue.value, onCheckedChange = {
                // Save user action in DataStore<Preference>
                coroutine.launch {
                    saveIsPushEnable(it, localContext)
                }

                // Subscribe and UnSubscribe push notification topic
                if (it) {
                    Firebase.messaging.subscribeToTopic("All").addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            localContext.showToast("Notification Enable.")
                        }
                    }
                } else {
                    Firebase.messaging.unsubscribeFromTopic("All").addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            localContext.showToast("Notification Disable.")
                        }
                    }
                }
            })
        }
    }
}

suspend fun saveIsPushEnable(switchValue: Boolean, localContext: Context) {
    //Create boolean preference key
    val isPushEnable = booleanPreferencesKey("isPushEnable")
    //dataStore is crate in context level as Singleton.
    // Check our DataStore Video.
    localContext.dataStore.edit {
        it[isPushEnable] = switchValue
    }
    // Now switch change it save the record.
}