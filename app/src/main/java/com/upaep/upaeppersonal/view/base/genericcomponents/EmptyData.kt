package com.upaep.upaeppersonal.view.base.genericcomponents

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.view.base.theme.roboto_bold

@Composable
fun EmptyData(message: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.icono_no_data),
            contentDescription = "Sin datos",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = message, textAlign = TextAlign.Center, fontFamily = roboto_bold, fontSize = 14.sp)
    }
}