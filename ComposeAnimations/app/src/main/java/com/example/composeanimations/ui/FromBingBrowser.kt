package com.example.composeanimations.ui

import android.Manifest
import android.preference.PreferenceManager
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionsScreen1() {
    val bluetoothPermissionState = rememberPermissionState(Manifest.permission.BLUETOOTH)
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    val permissionsState = listOf(bluetoothPermissionState, locationPermissionState)
    val permissionsToRequest =
        permissionsState.filter { it.status.shouldShowRationale || !it.status.isGranted }

    if (permissionsToRequest.isNotEmpty()) {
        permissionsToRequest.forEach { it.launchPermissionRequest() }
    }

    if (permissionsState.all { it.status.isGranted }) {
        Text("All permissions granted")
    } else {
        Text("Not all permissions granted")
    }
}

// To handle the case where the user allows Location permission only once,
// you can store the permission status in shared preferences or a local database
// and check it each time the app is launched. Here's an example code snippet:

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionsScreen2() {
    val bluetoothPermissionState = rememberPermissionState(Manifest.permission.BLUETOOTH)
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    val permissionsState = listOf(bluetoothPermissionState, locationPermissionState)
    val permissionsToRequest =
        permissionsState.filter { it.status.shouldShowRationale || !it.status.isGranted }

    if (permissionsToRequest.isNotEmpty()) {
        permissionsToRequest.forEach { it.launchPermissionRequest() }
    }

    val prefs = PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
    val locationPermissionGranted = prefs.getBoolean("LOCATION_PERMISSION_GRANTED", false)
    if (permissionsState.all { it.status.isGranted }) {
        if (locationPermissionState.status.isGranted && !locationPermissionGranted) {
            // Location permission has been granted for the first time
            prefs.edit().putBoolean("LOCATION_PERMISSION_GRANTED", true).apply()
        } else {
            Text("All permissions granted")
        }
    } else {
        Text("Not all permissions granted")
    }
}

// In this example, we first get a reference to the default shared preferences using
// the `LocalContext.current` property. We then check if the `LOCATION_PERMISSION_GRANTED`
// preference is `true` to determine if the Location permission has been granted before.
// If the Location permission has been granted for the first time, we update the preference
// to `true`. If the Location permission has already been granted, we display the
// "All permissions granted" message.

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MultiplePermission() {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val bluetoothPermissionState = rememberPermissionState(Manifest.permission.BLUETOOTH)
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(permissionState.toString(), bluetoothPermissionState.toString())
    )

    when {
        multiplePermissionsState.allPermissionsGranted -> {
            Text("Permission granted")
            // All permissions are granted
        }
        multiplePermissionsState.shouldShowRationale -> {
            // Show rationale and request permission again
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Permission required") },
                text = { Text("Please grant the required permissions") },
                confirmButton = {
                    Button(onClick = {
                        multiplePermissionsState.launchMultiplePermissionRequest()
                    }) {
                        Text("OK")
                    }
                }
            )
        }
        else -> {
            // Request permission again
            LaunchedEffect(permissionState) {
                if (!permissionState.status.isGranted) {
                    permissionState.launchPermissionRequest()
                }
            }
            LaunchedEffect(bluetoothPermissionState) {
                if (!bluetoothPermissionState.status.isGranted) {
                    bluetoothPermissionState.launchPermissionRequest()
                }
            }
        }
    }
}

// To handle all cases If the user allows location Only this time and when
// the next time the user will open the app, the permission request dialog again
// appears to the user, you can use the following code snippet:

/*
val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

val locationPermissionRequester =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Permission is granted
        } else {
            // Permission is denied
        }
    }

if (shouldShowRequestPermissionRationale(locationPermission)) {
    AlertDialog.Builder(this)
        .setTitle("Location permission")
        .setMessage("Location permission is required for this app")
        .setPositiveButton("OK") { _, _ ->
            locationPermissionRequester.launch(locationPermission)
        }
        .setNegativeButton("Cancel", null)
        .show()
} else {
    locationPermissionRequester.launch(locationPermission)
}*/
