package com.example.composeanimations.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.composeanimations.R
import com.google.accompanist.permissions.*

// Working Code "GetCameraPermission()"
@Composable
fun GetCameraPermission() {
    val context = LocalContext.current as ComponentActivity
    var shouldShowRationale by remember { mutableStateOf(true) }

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Granted, do your main work
            // onBtnClick()
        } else {
            if (context.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) && shouldShowRationale) {
                Toast.makeText(
                    context, "Camera permission rationale case!", Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context, "Kindly allow camera permission from settings!",
                    Toast.LENGTH_SHORT
                ).show()
                context.startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                )
            }
        }
    }

    Button(
        onClick = {
            // check camera permission here before callback or initiating the scan
            val cameraPermission = Manifest.permission.CAMERA
            val isCameraPermissionGranted = ContextCompat.checkSelfPermission(
                context,
                cameraPermission
            ) == PackageManager.PERMISSION_GRANTED

            if (isCameraPermissionGranted) {
                shouldShowRationale = false
                Toast.makeText(
                    context, "Camera permission granted!",
                    Toast.LENGTH_SHORT
                ).show()
                // onBtnClick()
            } else {
                if (shouldShowRationale) {
                    // Request camera permission again
                    requestCameraPermissionLauncher.launch(cameraPermission)
                } else {
                    shouldShowRationale = false
                }
            }
        }
    ) {
        Text(text = "Camera Permission")
    }
}


////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionCamera() {
    val context = LocalContext.current
    var shouldRequestPermission by remember { mutableStateOf(true) }

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA,
        onPermissionResult = { result ->
            println("Permission result: $result")
            shouldRequestPermission = false
        }
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(cameraPermissionState) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = object : DefaultLifecycleObserver {
            override fun onStop(owner: LifecycleOwner) {
                // Reset shouldRequestPermission flag when app is stopped
                if (!shouldRequestPermission && !cameraPermissionState.status.isGranted) {
                    shouldRequestPermission = true
                }
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(cameraPermissionState) {
        if (!shouldRequestPermission && !cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    when {
        cameraPermissionState.status.isGranted -> {
            Text(text = "Camera permission is granted")
        }
        cameraPermissionState.status.shouldShowRationale -> {
            AlertDialog(
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = false
                ),
                onDismissRequest = { },
                title = { Text(stringResource(id = R.string.access_camera)) },
                confirmButton = {
                    Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text(stringResource(id = R.string.label_allow))
                    }
                },
                dismissButton = {
                    Button(onClick = {}) {
                        Text(stringResource(id = R.string.label_deny))
                    }
                }
            )
        }
        else -> {
            AlertDialog(
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = false
                ),
                onDismissRequest = { },
                title = { Text(stringResource(id = R.string.permission_denied_camera)) },
                confirmButton = {
                    Button(onClick = {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                    }) {
                        Text(stringResource(id = R.string.label_setting))
                    }
                },
                dismissButton = {
                    Button(onClick = { }) {
                        Text(stringResource(id = R.string.label_decline))
                    }
                }
            )
        }
    }
}