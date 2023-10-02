package com.upaep.upaeppersonal.view.features.events

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.upaep.upaeppersonal.view.base.genericcomponents.CameraPermissionValidation
import com.upaep.upaeppersonal.view.base.genericcomponents.ScanningAndContent
import com.upaep.upaeppersonal.view.base.genericcomponents.ScanningView

@Preview
@Composable
fun EventsCamera() {
//    var scannedText by remember { mutableStateOf("") }
//    ScanningAndContent(content = {
//    }, onScanned = { scannedText = it })
    var scannedText by remember { mutableStateOf("") }
    var scanning by remember { mutableStateOf(true) }
    CameraPermissionValidation(
        content = {
            EventsCameraContent(
                scannedText = scannedText,
                onBtnClick = { scanning = true })
        },
        onScan = {
            scanning = false
            scannedText = it
        },
        outerScanning = scanning
    )
}

@Composable
fun EventsCameraContent(scannedText: String, onBtnClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = "Texto detectado: ")
            Text(text = scannedText)
            Button(onClick = { onBtnClick() }) {
                Text(text = "Volver a escannear")
            }
        }
    }
}