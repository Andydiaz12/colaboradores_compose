package com.upaep.upaeppersonal.view.base.genericcomponents

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun ScanningAndContent(
    content: @Composable () -> Unit,
    onScanned: (String) -> Unit,
    outerScanning: Boolean?
) {
    var scanning by remember { mutableStateOf(true) }
    if (outerScanning ?: scanning) {
        ScanningView(onScan = {
            scanning = false
            onScanned(it)
        })
    } else {
        content()
    }
}