package com.example.composeanimations

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
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
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.composeanimations.ui.Constants
import com.example.composeanimations.ui.GetPermissionBLEandLOC
import com.example.composeanimations.ui.theme.ComposeAnimationsTheme
import com.google.accompanist.permissions.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.tasks.Task

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    getPermissions(this, false)

                    /*if (checkCameraPermission(this)) {
                        // Main logic or main code
                        executeMainLogic()
                    } else {
                        requestCameraPermission(this, false)
                    }*/

                    GetPermissionBLEandLOC()

//                    QRScanner {}

//                    Button(onClick = { turnLocationOn(this) }) {
//                        Text("On")
//                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isEmpty()) return

        when (requestCode) {
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showBluetoothDialog(this)
                } else {
                    showPermissionDeniedDialog(this)
                }
            }
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_BLUETOOTH_SCAN -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showBluetoothDialog(this)
                } else {
                    showPermissionDeniedDialog(this)
                }
            }
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_BLUETOOTH_CONNECT -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent)
                }
            }
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
                        .show()
                    executeMainLogic()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                            PackageManager.PERMISSION_GRANTED
                        ) {
                            showCameraDialog(this)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_FINE_LOCATION -> {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                )
                    showPermissionDeniedDialog(this)
                else
                    showBluetoothDialog(this)
            }
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_BLUETOOTH_SCAN -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.BLUETOOTH_SCAN
                        ) != PackageManager.PERMISSION_GRANTED
                    )
                        showPermissionDeniedDialog(this)
                    else
                        showBluetoothDialog(this)
                }
            }
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA -> {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    showCameraDialog(this)
                }
            }
            Constants.REQUEST_CODE_LOCATION_SETTINGS_RESOLUTION -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(this, "Location on!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Activity.RESULT_CANCELED -> {
                        Toast.makeText(this, "Please turn on location!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) showPermissionDeniedDialog(this) else showBluetoothDialog(this)
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) showPermissionDeniedDialog(this) else showBluetoothDialog(this)
        }

        if (requestCode == Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                showMessageOKCancel(this) { dialog, which ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestCameraPermission(this)
                    }
                }
            }
        }*/
    }
}


/////////////////////////////////////////////////////////////////////////////////
// ============================ Local Code =================================== //
/////////////////////////////////////////////////////////////////////////////////


fun checkCameraPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestCameraPermission(context: Context, isDeniedOnce: Boolean) {
    val cameraPermission = arrayOf(Manifest.permission.CAMERA)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (!isDeniedOnce) {
                ActivityCompat.requestPermissions(
                    context as ComponentActivity,
                    cameraPermission,
                    Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA
                )
            } else {
                if ((context as ComponentActivity).shouldShowRequestPermissionRationale(
                        Manifest.permission.CAMERA
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        context,
                        cameraPermission,
                        Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA
                    )
                } else {
                    takeUserToAppSettings(context)
                }
            }
        }
    } else {
        ActivityCompat.requestPermissions(
            context as ComponentActivity,
            arrayOf(Manifest.permission.CAMERA),
            Constants.REQUEST_PERMISSIONS_REQUEST_CODE_CAMERA
        )
    }
}

fun showCameraDialog(context: Context) {
    if (checkCameraPermission(context)) return
    val alertCamera = AlertDialog.Builder(context)
    alertCamera.setMessage(R.string.access_camera)
    alertCamera.setCancelable(false)
    alertCamera.setNegativeButton(R.string.label_cancel) { dialog, which ->
        (context as ComponentActivity).finish()
    }
    alertCamera.setPositiveButton(R.string.label_retry) { dialog, which ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestCameraPermission(context, true)
            dialog.dismiss()
        }
    }
    alertCamera.create().show()
}

fun executeMainLogic() {
    // Main logic or main code
    println("Camera Permission Granted")
}


/////////////////////////////////////////////////////////////////////////////////
// ============================ Local Code =================================== //
/////////////////////////////////////////////////////////////////////////////////


@RequiresApi(Build.VERSION_CODES.S)
fun getPermissions(context: Context, isDeniedOnce: Boolean) {

    val blePermissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    val blePermissions12 = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT
    )

    val GRANTED = PackageManager.PERMISSION_GRANTED

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN)
            != GRANTED
        ) {
            if (!isDeniedOnce) {
                ActivityCompat.requestPermissions(
                    context as ComponentActivity,
                    blePermissions12,
                    Constants.REQUEST_PERMISSIONS_REQUEST_CODE_BLUETOOTH_SCAN
                )
            } else {
                if ((context as ComponentActivity).shouldShowRequestPermissionRationale(
                        Manifest.permission.BLUETOOTH_SCAN
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        context,
                        blePermissions12,
                        Constants.REQUEST_PERMISSIONS_REQUEST_CODE_BLUETOOTH_SCAN
                    )
                } else {
                    takeUserToAppSettings(context)
                }
            }
        } else {
            showBluetoothDialog(context)
        }
    } else {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != GRANTED
        ) {
            if (!isDeniedOnce) {
                ActivityCompat.requestPermissions(
                    context as ComponentActivity,
                    blePermissions,
                    Constants.REQUEST_PERMISSIONS_REQUEST_CODE_FINE_LOCATION
                )
            } else {
                if ((context as ComponentActivity).shouldShowRequestPermissionRationale(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    ActivityCompat.requestPermissions(
                        context,
                        blePermissions,
                        Constants.REQUEST_PERMISSIONS_REQUEST_CODE_FINE_LOCATION
                    )
                } else {
                    takeUserToAppSettings(context)
                }
            }
        } else {
            showBluetoothDialog(context)
        }
    }
}

fun showBluetoothDialog(context: Context) {
    if (isBluetoothEnabled(context)) return
    val alertBluetooth = AlertDialog.Builder(context)
    alertBluetooth.setMessage(R.string.activate_bluetooth)
    alertBluetooth.setCancelable(false)
    alertBluetooth.setNegativeButton(R.string.label_setting) { dialog, which ->
        startActivityForResult(
            context as ComponentActivity,
            Intent(Settings.ACTION_BLUETOOTH_SETTINGS),
            0, null
        )
    }
    alertBluetooth.setPositiveButton(
        R.string.label_ok
    ) { dialog, which -> dialog.dismiss() }

    alertBluetooth.setOnDismissListener {
        showLocationDialog(context)
    }

    alertBluetooth.create().show()
}

fun showLocationDialog(context: Context) {
    if (isLocationServiceEnable(context)) return // for composable
    val alertLocation = AlertDialog.Builder(context)
    alertLocation.setMessage(R.string.activate_gps)
    alertLocation.setCancelable(false)
    alertLocation.setNegativeButton(R.string.label_setting) { dialog, which ->
        startActivityForResult(
            context as ComponentActivity,
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
            0, null
        )
    }
    alertLocation.setPositiveButton(R.string.label_ok) { dialog, which ->
        dialog.dismiss()
    }

    alertLocation.show()
}

@RequiresApi(Build.VERSION_CODES.S)
fun showPermissionDeniedDialog(context: Context) {
    val dialogBLE = AlertDialog.Builder(context)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        dialogBLE.setMessage(R.string.permission_bluetooth)
    else
        dialogBLE.setMessage(R.string.permission_location)
    dialogBLE.setCancelable(false)
    dialogBLE.setNegativeButton(R.string.label_cancel) { dialog, which ->
        (context as ComponentActivity).finish()
    }
    dialogBLE.setPositiveButton(R.string.label_retry) { dialog, which ->
        getPermissions(context, true)
        dialog.dismiss()
    }
    dialogBLE.create().show()
}

fun takeUserToAppSettings(context: Context) {
    val getPermIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", context.packageName, null)
    getPermIntent.data = uri
    startActivityForResult(context as ComponentActivity, getPermIntent, 123, null)
}

fun isLocationServiceEnable(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val gps: Boolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val network: Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    return gps || network
}

fun isBluetoothEnabled(ctx: Context): Boolean {
    val bluetoothManager = ctx.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter = bluetoothManager.adapter
    return bluetoothAdapter != null && bluetoothAdapter.isEnabled
}

fun turnedBluetoothOn(ctx: Context) {
    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                ctx as ComponentActivity,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                Constants.REQUEST_PERMISSIONS_REQUEST_CODE_BLUETOOTH_CONNECT
            )
            return
        }
    }
    startActivity(ctx, intent, null)
}

@SuppressLint("VisibleForTests")
@RequiresApi(Build.VERSION_CODES.S)
fun turnLocationOn(context: Context) {

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

    result.addOnCompleteListener { task ->
        try {
            val response: LocationSettingsResponse = task.getResult(ApiException::class.java)
            if (response.locationSettingsStates.isLocationUsable) {
                // Location is usable, perform your desired action
                // For example, start location updates or proceed with location-dependent functionality
                Toast.makeText(context, "Location is already on", Toast.LENGTH_SHORT).show()
            } else {
                // Location is not usable, start the resolution process
                // For example, display an error message or prompt the user to enable location services
                Toast.makeText(context, "Start the resolution process", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            when (e.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    try {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(
                            context as ComponentActivity,
                            Constants.REQUEST_CODE_LOCATION_SETTINGS_RESOLUTION
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
}


/////////////////////////////////////////////////////////////////////////////////
// ============================ Compose Code ================================= //
/////////////////////////////////////////////////////////////////////////////////


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun GetPermission(isDeniedOnce: Boolean) {
    val context = LocalContext.current as ComponentActivity
    val blePermissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val blePermissions12 = arrayOf(
        Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT
    )

    val GRANTED = PackageManager.PERMISSION_GRANTED

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isBluetoothScanGranted =
            permissions[Manifest.permission.BLUETOOTH_SCAN] ?: false
        val isAccessFineLocationGranted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

        if (isBluetoothScanGranted) {
            showBluetoothDialog(context)
        } else if (isAccessFineLocationGranted) {
            showLocationDialog(context)
        } else {
            takeUserToAppSettings(context)
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN)
                != GRANTED
            ) {
                if (!isDeniedOnce) {
                    permissionLauncher.launch(blePermissions12)
                } else {
                    if ((context).shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_SCAN)
                    ) {
                        permissionLauncher.launch(blePermissions12)
                    } else {
                        takeUserToAppSettings(context)
                    }
                }
            } else {
                showBluetoothDialog(context)
            }
        } else {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != GRANTED
            ) {
                if (!isDeniedOnce) {
                    permissionLauncher.launch(blePermissions)
                } else {
                    if ((context).shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                    ) {
                        permissionLauncher.launch(blePermissions)
                    } else {
                        takeUserToAppSettings(context)
                    }
                }
            } else {
                showBluetoothDialog(context)
            }
        }
    }
}

@Composable
fun ShowBluetoothDialog() {
    val context = LocalContext.current as ComponentActivity
    if (com.example.composeanimations.isBluetoothEnabled(context)) return
    val showDialog = remember { mutableStateOf(true) }

    // Request Bluetooth activation
    /*val enableBluetoothLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            if (isGPSOn(context)) {
                showLocationDialog(context)
            }
        }*/

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Activate Bluetooth") },
            text = { Text(text = stringResource(id = R.string.activate_bluetooth)) },
            confirmButton = {
                Button(onClick = {
                    /*val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    enableBluetoothLauncher.launch(enableBluetoothIntent)*/
                    takeUserToAppSettings(context)
                    showDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.label_setting))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text(text = stringResource(id = R.string.label_ok))
                }
            }
        )
    }
}

@Composable
fun ShowLocationDialog() {
    val context = LocalContext.current as ComponentActivity
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Activate Location") },
            text = { Text(text = stringResource(id = R.string.activate_gps)) },
            confirmButton = {
                Button(onClick = { takeUserToAppSettings(context) }) {
                    Text(text = stringResource(id = R.string.label_setting))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text(text = stringResource(id = R.string.label_ok))
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ShowPermissionDeniedDialog() {
    val context = LocalContext.current as ComponentActivity
    val showDialog = remember { mutableStateOf(true) }

    // Request permissions
    val requestPermissionsLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allPermissionsGranted = permissions.values.all { it }
            if (allPermissionsGranted) {
                // Permissions granted, handle accordingly
            } else {
                // Permissions not granted, show permission denied dialog again
                showDialog.value = true
            }
        }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Text(text = stringResource(id = R.string.permission_bluetooth))
                } else {
                    Text(text = stringResource(id = R.string.permission_location))
                }
            },
            confirmButton = {
                Button(onClick = {
                    requestPermissionsLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                    getPermissions(context, true)
                    showDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.label_retry))
                }
            },
            dismissButton = {
                Button(onClick = { (context).finish() }) {
                    Text(text = stringResource(id = R.string.label_cancel))
                }
            },
        )
    }
}

@Composable
fun TakeUserToAppSettings(context: Context) {
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        // Handle activity result if needed
    }

    LaunchedEffect(Unit) {
        val getPermIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        getPermIntent.data = uri
        activityResultLauncher.launch(getPermIntent)
    }
}

