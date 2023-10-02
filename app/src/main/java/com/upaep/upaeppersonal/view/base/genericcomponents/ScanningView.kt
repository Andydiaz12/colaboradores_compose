package com.upaep.upaeppersonal.view.base.genericcomponents

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@Composable
fun ScanningView(onScan: (String) -> Unit) {
    var scanFlag by remember { mutableStateOf(false) }
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
                        onScan(barCodeOrQr)
                        scanFlag = false
                    }
                }
                this.resume()
            }
        },
        modifier = Modifier
    )
}