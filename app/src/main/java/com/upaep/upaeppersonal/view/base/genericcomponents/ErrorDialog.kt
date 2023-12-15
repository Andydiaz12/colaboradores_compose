package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorDialog(message: String, onCloseDialog: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onCloseDialog() },
        text = {
            Card(colors = CardDefaults.cardColors(
                containerColor = Color.White
            )) {
                Text(text = message)
            }
        },
        dismissButton = {
            Text(
                text = "Cerrar",
                modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onCloseDialog() })
            )
        },
        confirmButton = { },
        containerColor = Color.White
    )
}