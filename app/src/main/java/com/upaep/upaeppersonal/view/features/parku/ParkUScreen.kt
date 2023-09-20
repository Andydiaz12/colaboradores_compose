package com.upaep.upaeppersonal.view.features.parku

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.upaep.upaeppersonal.viewmodel.features.parku.ParkUViewModel

@Preview
@Composable
fun ParkUScreen(parkUViewModel: ParkUViewModel = hiltViewModel()) {
//    Testing()
    val scanning = remember { mutableStateOf(true) }
    val scannedText = remember { mutableStateOf("") }
    if (scanning.value) {
        AdminClubMembershipScanScreen(onScan = {
            scannedText.value = it
            scanning.value = false
        })
    } else {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(text = "Texto detectado: ")
                Text(text = scannedText.value)
            }
        }
    }
}

@Composable
fun Testing() {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // El permiso ha sido otorgado en este Composable.
            // Puedes realizar las acciones relacionadas con el permiso aquí.
        } else {
            // El permiso no ha sido otorgado, maneja este caso según tus necesidades.
        }
    }

    // Solicitar el permiso cuando el Composable se muestra por primera vez.
    DisposableEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
        onDispose { /* Limpieza si es necesario */ }
    }

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    ) {
    } else {
    }
}

@Composable
fun AdminClubMembershipScanScreen(onScan: (String) -> Unit) {
    var scanFlag by remember {
        mutableStateOf(false)
    }

    AndroidView(
        factory = { context ->
            CompoundBarcodeView(context).apply {
                val capture = CaptureManager(context as Activity, this)
                capture.initializeFromIntent(context.intent, null)
                this.setStatusText("")
                capture.decode()
                this.decodeContinuous { result ->
                    if (scanFlag) {
                        return@decodeContinuous
                    }
                    scanFlag = true
                    result.text?.let { barCodeOrQr ->
                        //Do something and when you finish this something
                        //put scanFlag = false to scan another item
                        onScan(barCodeOrQr)
                        scanFlag = false
                    }
                    //If you don't put this scanFlag = false, it will never work again.
                    //you can put a delay over 2 seconds and then scanFlag = false to prevent multiple scanning
                }
                this.resume()
            }
        },
        modifier = Modifier
    )
}