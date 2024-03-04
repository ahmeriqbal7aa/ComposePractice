package com.example.composeanimations.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.LocationRequest
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.composeanimations.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.tasks.Task


// Working Code "GetPermissionBLEandLOC()"
@SuppressLint("VisibleForTests")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun GetPermissionBLEandLOC() {
    val context = LocalContext.current as ComponentActivity
    val locPermission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val blePermissions12 = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT
    )

    val locationRequest = com.google.android.gms.location.LocationRequest().apply {
        priority = LocationRequest.QUALITY_HIGH_ACCURACY
        interval = 5000
        fastestInterval = 2000
    }
    val locationSettingsRequestBuilder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
    locationSettingsRequestBuilder.setAlwaysShow(true)
    val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(context)
        .checkLocationSettings(locationSettingsRequestBuilder.build())

    var shouldShowBleRationale by remember { mutableStateOf(true) }
    var shouldShowLocRationale by remember { mutableStateOf(true) }

    // Request Bluetooth activation
    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == ComponentActivity.RESULT_OK) {
            println("Bluetooth enabled successfully")
            // Bluetooth enabled successfully
        } else {
            println("Bluetooth not enabled")
            // Bluetooth not enabled
        }
    }

    // Request Location activation
    val resolutionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            // Location settings resolved, perform desired action
            Toast.makeText(context, "Location on!", Toast.LENGTH_SHORT).show()
        } else {
            // Location settings not resolved, handle the case accordingly
            Toast.makeText(context, "Please turn on location!", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isBluetoothScanGranted =
            permissions[Manifest.permission.BLUETOOTH_SCAN] ?: false
        val isBluetoothConnectGranted =
            permissions[Manifest.permission.BLUETOOTH_CONNECT] ?: false
        val isAccessFineLocationGranted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (isBluetoothScanGranted && isBluetoothConnectGranted) {
                if (isBluetoothOn(context)) {
                    // onAddBaseStation()
                    return@rememberLauncherForActivityResult
                }
            } else {
                if (context.shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_SCAN) && shouldShowBleRationale) {
                    Toast.makeText(
                        context, "Bluetooth permission rationale case!", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context, "Kindly allow bluetooth permission from settings!",
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
        } else {
            if (isAccessFineLocationGranted) {
                if (isLocationOn(context)) {
                    if (isBluetoothOn(context)) {
                        // onAddBaseStation()
                        return@rememberLauncherForActivityResult
                    }
                }
            } else {
                if (context.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) && shouldShowLocRationale) {
                    Toast.makeText(
                        context, "Location permission rationale case!", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context, "Kindly allow location permission from settings!",
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
    }

    Button(onClick = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val isBleScanPermissionGranted = ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED

            val isBleConnectPermissionGranted = ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED

            if (isBleScanPermissionGranted && isBleConnectPermissionGranted) {
                shouldShowBleRationale = false
                if (isBluetoothOn(context)) {
                    // onAddBaseStation()
                } else {
                    // kindly turn on bluetooth
                    val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    enableBluetoothLauncher.launch(enableBluetoothIntent)
                }
            } else {
                if (shouldShowBleRationale) {
                    permissionLauncher.launch(blePermissions12)
                } else {
                    shouldShowBleRationale = false
                }
            }
        } else {
            val isLocPermissionGranted = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (isLocPermissionGranted) {
                shouldShowLocRationale = false
                if (isLocationOn(context)) {
                    if (isBluetoothOn(context)) {
                        // onAddBaseStation()
                    } else {
                        // kindly turn on bluetooth
                        val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        enableBluetoothLauncher.launch(enableBluetoothIntent)
                    }
                } else {
                    // kindly turn on location
                    try {
                        val response: LocationSettingsResponse =
                            result.getResult(ApiException::class.java)
                        if (response.locationSettingsStates.isLocationUsable) {
                            // Perform your desired action
                            Toast.makeText(context, "Location is already on", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            // Start the resolution process
                        }
                    } catch (e: ApiException) {
                        when (e.statusCode) {
                            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                                try {
                                    val resolvableApiException = e as ResolvableApiException
                                    resolutionLauncher.launch(
                                        IntentSenderRequest.Builder(resolvableApiException.resolution.intentSender)
                                            .build()
                                    )
                                } catch (ex: IntentSender.SendIntentException) {
                                    ex.printStackTrace()
                                }
                            }
                            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                                // Device does not have location
                            }
                        }
                    }
                }
            } else {
                if (shouldShowBleRationale) {
                    permissionLauncher.launch(locPermission)
                } else {
                    shouldShowBleRationale = false
                }
            }
        }
    }) {
        Text(text = "Permission")
    }
}


fun isLocationOn(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val gps: Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val network: Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    return gps || network
}

fun isBluetoothOn(ctx: Context): Boolean {
    val bluetoothManager = ctx.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter = bluetoothManager.adapter
    return bluetoothAdapter != null && bluetoothAdapter.isEnabled
}
