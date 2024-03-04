package com.example.composeanimations.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.composeanimations.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


@SuppressLint("UnsafeOptInUsageError")
class BarCodeAnalyser(
// private val onBarcodeDetected: (barcodes: List<Barcode>) -> Unit, // multiple bar code
    private val onBarcodeDetected: (barcodes: Barcode) -> Unit,
) : ImageAnalysis.Analyzer {
    private var lastAnalyzedTimeStamp = 0L

    private var detected = false
    override fun analyze(image: ImageProxy) {
        if (detected) {
            image.close()
            return
        }

        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimeStamp >= TimeUnit.SECONDS.toMillis(1)) {
            image.image?.let { imageToAnalyze ->
                val options =
                    BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                        .build()

                val barcodeScanner = BarcodeScanning.getClient(options)
                val imageToProcess =
                    InputImage.fromMediaImage(imageToAnalyze, image.imageInfo.rotationDegrees)

                barcodeScanner.process(imageToProcess).addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        detected = true
                        onBarcodeDetected(barcodes[0])
                        // onBarcodeDetected(barcodes[) // multiple bar code
                    } else {
                        Log.d("TAG", "analyze: No barcode Scanned")
                    }
                }.addOnFailureListener { exception ->
                    Log.d("TAG", "BarcodeAnalyser: Something went wrong $exception")
                }.addOnCompleteListener {
                    image.close()
                }
            }
            lastAnalyzedTimeStamp = currentTimestamp
        } else {
            image.close()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRScanner(barcodeReceived: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }
// val barCodeVal = remember { mutableStateOf("") }

    var shouldRequestPermission by remember { mutableStateOf(true) }

    val cameraPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA,
        onPermissionResult = { result ->
            println("Permission result: $result")
            shouldRequestPermission = false
        }
    )

    DisposableEffect(Unit) {
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
            AndroidView(factory = { AndroidViewContext ->
                PreviewView(AndroidViewContext).apply {

                    this.scaleType = PreviewView.ScaleType.FILL_CENTER
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }
            }, modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
                update = { previewView ->
                    val cameraSelector: CameraSelector =
                        CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                    val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
                    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                        ProcessCameraProvider.getInstance(context)

                    cameraProviderFuture.addListener({
                        preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                        val barcodeAnalyser = BarCodeAnalyser { barcode ->

                            barcode.rawValue?.let { barcodeValue ->
                                // val mac = barcodeValue.chunked(2).joinToString(separator = ":")
                                Toast.makeText(context, barcodeValue, Toast.LENGTH_LONG).show()
                                barcodeReceived(barcodeValue)
                            }


                            // for multiple barcode
//                            barcodes.forEach { barcode ->
//                                barcode.rawValue?.let { barcodeValue ->
//                                  barCodeVal.value = barcodeValue
//                                    barcodeReceived(barcodeValue)
//
//                                }
//                            }

                        }
                        val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                            .also {
                                it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                            }
                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner, cameraSelector, preview, imageAnalysis
                            )
                        } catch (e: Exception) {
                            Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                        }
                    }, ContextCompat.getMainExecutor(context))
                })
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
