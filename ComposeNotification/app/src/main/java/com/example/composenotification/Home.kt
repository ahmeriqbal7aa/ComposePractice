package com.example.composenotification

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(intent: Intent, activity: MainActivity) {

    //Let's Print FCM token in LogCat
    //Read FCM from DataStore<Preference>
    //And show on screen.

    val context = LocalContext.current
    val fcmToken = flow {
        context.dataStore.data.map {
            it[stringPreferencesKey(FCM_TOKEN_PREF_KEY)]
        }.collect(collector = {
            if (it != null) {
                //Log.d("$LOG_TAG-Home", "fcmToken : $it")
                this.emit(it)
            }
        })
    }.collectAsState(initial = "")


    // Read notification payload when click on notification
    val notificationTitle = remember {
        mutableStateOf(
            if (intent.hasExtra("title")) intent.getStringExtra("title") else ""
        )
    }

    val notificationBody = remember {
        mutableStateOf(
            if (intent.hasExtra("body")) intent.getStringExtra("body") else ""
        )
    }

    // Create two mutable state for remember radio button state
    val topicAllSelected = remember { mutableStateOf(false) }
    val topicNewsSelected = remember { mutableStateOf(false) }

    Scaffold(topBar = { SmallTopAppBar(title = { Text(text = "Push Notification.") }) }) {
        Box(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "Topic messaging on Android ")

                Row(modifier = Modifier.fillMaxWidth()) {

                    // set mutableState on selected property.
                    RadioButton(selected = topicAllSelected.value, onClick = {
                        topicAllSelected.value = !topicAllSelected.value
                        //Subscribe  the client app to topic
                        // Now Add add complete listener that listener will help
                        // that topic which we have subscribe it successful or failed.

                        if (topicAllSelected.value) {
                            //if it is true then subscribe it ALL topic.
                            Firebase.messaging.subscribeToTopic("All")
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        context.showToast("All topic subscribe successfully")
                                    } else {
                                        context.showToast("All topic subscribe failed.")
                                        // if it failed then we have revert back state of radio button to its periods state.
                                        // means unselected.
                                        topicAllSelected.value = false
                                    }
                                }
                        } else {
                            // if it false then unsubscribe it.
                            // like above we have add listener too for below method.
                            Firebase.messaging.unsubscribeFromTopic("All")
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        context.showToast("All topic unsubscribe successfully")
                                    } else {
                                        context.showToast("All topic unsubscribe failed.")
                                        topicAllSelected.value = true
                                    }
                                }
                        }
                    })

                    // Fix Alignment for this text field.
                    Text(
                        text = "Topic All", textAlign = TextAlign.Center,
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                }

                // Now we code like same as we do for All topics and listener too.
                Row(modifier = Modifier.fillMaxWidth()) {
                    // set mutableState on selected property.
                    RadioButton(selected = topicNewsSelected.value, onClick = {
                        topicNewsSelected.value = !topicNewsSelected.value
                        // Now when user select News topic then we have do same
                        //as we have do with ALL topic selection.

                        if (topicNewsSelected.value) {
                            Firebase.messaging.subscribeToTopic("News")
                                .addOnCompleteListener { task ->
                                    // update toast message.
                                    if (task.isSuccessful) {
                                        context.showToast("News topic subscribe successfully")
                                    } else {
                                        context.showToast("News topic subscribe failed.")
                                        // if it failed then we have revert back state of radio button to its perious state.
                                        // means unselected.
                                        topicNewsSelected.value = false
                                    }
                                }
                        } else {
                            Firebase.messaging.unsubscribeFromTopic("News")
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        context.showToast("News topic unsubscribe successfully")
                                    } else {
                                        context.showToast("News unsubscribe failed.")
                                        topicNewsSelected.value = true

                                    }
                                }
                        }
                    })

                    // Now fix it same as we do for above text field.
                    Text(
                        text = "Topic News",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "FCM TOKEN : GET ON APP INSTALL")
                Text(text = fcmToken.value)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Title: ${notificationTitle.value}")
                Text(text = "Body : ${notificationBody.value}")
            }
        }
    }
}