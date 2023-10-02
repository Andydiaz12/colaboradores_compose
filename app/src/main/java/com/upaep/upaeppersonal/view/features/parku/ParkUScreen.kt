package com.upaep.upaeppersonal.view.features.parku

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.upaeppersonal.view.base.genericcomponents.CameraPermissionValidation
import com.upaep.upaeppersonal.view.base.genericcomponents.ScanningView
import com.upaep.upaeppersonal.viewmodel.features.parku.ParkUViewModel

@Preview
@Composable
fun ParkUScreen(parkUViewModel: ParkUViewModel = hiltViewModel()) {
    var scannedText by remember { mutableStateOf("") }
    CameraPermissionValidation(
        content = { ParkUContent(scannedText = scannedText) },
        onScan = {}
    )
}

@Composable
fun ParkUContent(scannedText: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = "Texto detectado: ")
            Text(text = scannedText)
        }
    }
}