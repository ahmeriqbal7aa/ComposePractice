package com.example.composenotification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.composenotification.nav_deeplink_onClick_notification.NavigationPage
import com.example.composenotification.ui.theme.ComposeNotificationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyNotification.createNotificationChannel(applicationContext)
        setContent {
            ComposeNotificationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //NavigationPage() // navigation when click on notification banner
                    Home(intent, this@MainActivity)
                }
            }
        }
    }
}

/*val requestPermissionLauncher = registerForActivityResult(
    ActivityResultContracts.RequestPermission(),
) { isGranted: Boolean ->
    if (isGranted) {
        Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT)
            .show()
    } else {
        Toast.makeText(applicationContext, "Not Granted", Toast.LENGTH_SHORT)
            .show()
    }
}

fun askNotificationPermission() {
    // This is only necessary for API level >= 33 (TIRAMISU)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT)
                .show()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
            Toast.makeText(applicationContext, "Rationale", Toast.LENGTH_SHORT)
                .show()
        } else {
            // Directly ask for the permission
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}*/
