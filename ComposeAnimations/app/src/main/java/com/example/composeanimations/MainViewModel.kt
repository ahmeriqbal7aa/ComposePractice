package com.example.composeanimations

import android.Manifest
import android.os.Build
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}

/*private val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    arrayOf(
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
} else {
    arrayOf(
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )
}*/

/*val viewModel = viewModel<MainViewModel>()
val dialogQueue = viewModel.visiblePermissionDialogQueue

val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions(),
    onResult = { perms ->
        permissionsToRequest.forEach { permission ->
            viewModel.onPermissionResult(
                permission = permission,
                isGranted = perms[permission] == true
            )
        }
    }
)

Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Button(onClick = {
        multiplePermissionResultLauncher.launch(permissionsToRequest)
    }) {
        Text(text = "Request multiple permission")
    }
}

// the first dialog we want to see is the dialog that is last in our list so we reversed()

dialogQueue
    .reversed()
    .forEach { permission ->
        PermissionDialog(
            permissionTextProvider = when (permission) {
                Manifest.permission.BLUETOOTH_SCAN -> {
                    BluetoothPermissionTextProvider()
                }
                Manifest.permission.BLUETOOTH_CONNECT -> {
                    BluetoothPermissionTextProvider()
                }
                Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    LocationPermissionTextProvider()
                }
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    LocationPermissionTextProvider()
                }
                else -> return@forEach
            },
            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                permission
            ),
            onDismiss = viewModel::dismissDialog,
            onOkClick = {
                viewModel.dismissDialog()
                multiplePermissionResultLauncher.launch(
                    arrayOf(permission)
                )
            },
            onGoToAppSettingsClick = ::openAppSettings
        )
    }*/



/*val permissionState =
    rememberPermissionState(permission = permission.ACCESS_FINE_LOCATION)

if (permissionState.status.isGranted) {
    Toast.makeText(context, "isGranted", Toast.LENGTH_SHORT).show()
} else {
    if (permissionState.status.shouldShowRationale) {
        Toast.makeText(context, "shouldShowRationale", Toast.LENGTH_SHORT).show()
    }else{
        Toast.makeText(context, "settings", Toast.LENGTH_SHORT).show()
    }
}*/