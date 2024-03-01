package com.example.composeelements

import android.util.Log
import androidx.compose.runtime.Composable

@Composable
fun GetEnvironmentVariable() {

    /*Log.e("Tweetsy", "---------------------------------------")

    System.setProperty("MY_ENV", "The Test Value")

    Log.e("Tweetsy : getProperty", System.getProperty("MY_ENV")?.toString() ?: "Null")
    Log.e("Tweetsy : getProperty", System.getProperty("user.dir")?.toString() ?: "Null")
    Log.e("Tweetsy : getProperty", System.getProperty("home")?.toString() ?: "Null")
    Log.e(
        "Tweetsy : getProperty",
        System.getProperty("java.runtime.version")?.toString() ?: "Null"
    )*/

    Log.e("Tweetsy", "---------------------------------------")

    /*val envVariables = System.getenv()
    for ((key, value) in envVariables) {
        Log.e("Tweetsy : getenv", "$key=$value")
    }*/

    Log.e("Tweetsy : getenv", BuildConfig.TEST_ENV)
    Log.e("Tweetsy : getenv", BuildConfig.TEST_ENVS)
    Log.e("Tweetsy : getenv", System.getenv("MY_ENV_VALUE")?.toString() ?: "Null")

    Log.e("Tweetsy", "---------------------------------------")
}