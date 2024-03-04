package com.example.composeanimations.ui

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.composeanimations.*
import com.example.composeanimations.R
import com.google.accompanist.permissions.*


fun isBluetoothEnabled(ctx: Context): Boolean {
    val bluetoothManager = ctx.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter = bluetoothManager.adapter
    return bluetoothAdapter != null && bluetoothAdapter.isEnabled
}

fun isLocationEnabled(ctx: Context): Boolean {
    val locationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

fun isLocationServiceEnable(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val gps: Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val network: Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    return gps || network
}

fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@TargetApi(Build.VERSION_CODES.S)
private fun hasBlePermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.BLUETOOTH_CONNECT
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

private fun Activity.takeUserToAppSettings() {
    val getPermIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", packageName, null)
    getPermIntent.data = uri
    startActivityForResult(getPermIntent, 123)
}

fun getVersionInfo(context: Context): String {

    val packageManager: PackageManager = context.packageManager

    var packInfo: PackageInfo? = null
    try {
        packInfo = packageManager.getPackageInfo(context.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return if (packInfo != null) {
        packInfo.versionName
    } else ""
}

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionBLEandLOC() {
    val context = LocalContext.current as ComponentActivity
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        permissionStates.permissions.forEach {
            when (it.permission) {
                Manifest.permission.BLUETOOTH_SCAN -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        if (it.status.isGranted) {
                            Text(text = "Bluetooth Granted")
                        } else {
                            if (it.status.shouldShowRationale) {
                                AlertDialog(
                                    properties = DialogProperties(
                                        dismissOnBackPress = true,
                                        dismissOnClickOutside = false
                                    ),
                                    onDismissRequest = { },
                                    title = { Text(text = "Both permission is needed") },
                                    confirmButton = {
                                        Button(onClick = { it.launchPermissionRequest() }) {
                                            Text(stringResource(id = R.string.label_allow))
                                        }
                                    },
                                )
                            } else {
                                AlertDialog(
                                    properties = DialogProperties(
                                        dismissOnBackPress = true,
                                        dismissOnClickOutside = false
                                    ),
                                    onDismissRequest = { },
                                    title = { Text(text = "Both permission is denied") },
                                    confirmButton = {
                                        Button(onClick = {
                                            context.startActivity(
                                                Intent(
                                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                    Uri.fromParts(
                                                        "package",
                                                        context.packageName,
                                                        null
                                                    )
                                                )
                                            )
                                        }) {
                                            Text(stringResource(id = R.string.label_setting))
                                        }
                                    },
                                )
                            }
                        }
                    }
                }
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                        if (it.status.isGranted) {
                            Text(text = "Location Granted")
                        } else {
                            if (it.status.shouldShowRationale) {
                                AlertDialog(
                                    properties = DialogProperties(
                                        dismissOnBackPress = true,
                                        dismissOnClickOutside = false
                                    ),
                                    onDismissRequest = { },
                                    title = { Text(text = "Location permission is needed") },
                                    confirmButton = {
                                        Button(onClick = { it.launchPermissionRequest() }) {
                                            Text(stringResource(id = R.string.label_allow))
                                        }
                                    },
                                )
                            } else {
                                AlertDialog(
                                    properties = DialogProperties(
                                        dismissOnBackPress = true,
                                        dismissOnClickOutside = false
                                    ),
                                    onDismissRequest = { },
                                    title = { Text(text = "Location permission is denied") },
                                    confirmButton = {
                                        Button(onClick = {
                                            context.startActivity(
                                                Intent(
                                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                    Uri.fromParts(
                                                        "package",
                                                        context.packageName,
                                                        null
                                                    )
                                                )
                                            )
                                        }) {
                                            Text(stringResource(id = R.string.label_setting))
                                        }
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
