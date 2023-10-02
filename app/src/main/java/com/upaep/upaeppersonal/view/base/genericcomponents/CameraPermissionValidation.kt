package com.upaep.upaeppersonal.view.base.genericcomponents

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.upaep.upaeppersonal.view.base.theme.roboto_regular

@Composable
fun CameraPermissionValidation(
    content: @Composable () -> Unit,
    onScan: (String) -> Unit,
    outerScanning: Boolean? = null
) {
    val context = LocalContext.current
    var displayComposable by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean -> displayComposable = isGranted }

    DisposableEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
        onDispose { /* Limpieza si es necesario */ }
    }

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        if (!displayComposable) ScanningAndContent(
            onScanned = onScan,
            content = content,
            outerScanning = outerScanning
        )
    } else {
        AlertDialog(
            onDismissRequest = { launcher.launch(Manifest.permission.CAMERA) },
            confirmButton = {
                TextButton(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {

            },
            text = {
                Text(
                    text = "Se necesitan los permisos de cámara para ocupar esta sección",
                    fontFamily = roboto_regular
                )
            })
    }
    if (displayComposable) ScanningAndContent(
        onScanned = onScan,
        content = content,
        outerScanning = outerScanning
    )
}