package com.upaep.upaeppersonal.view.features.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import java.text.DateFormat

@Preview
@Composable
fun AboutThisAppScreen() {
    BaseView {
//        AboutSectionImage()
        AboutSectionText(text = "Versión 1.3")
        AboutSectionText(text = "Innovación y Desarrollo Digital", fontFamily = roboto_regular)
//        AboutSectionImage()
        AboutSectionText(text = "Fecha de actualización")
        AboutSectionText(text = "")
//        AboutSectionImage()
        AboutSectionText(text = "Soporte")
        AboutSectionText(text = "idd@upaep.mx", fontFamily = roboto_regular)
    }
}

@Composable
fun AboutSectionText(text: String, fontFamily: FontFamily = roboto_bold) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontFamily = fontFamily,
        fontSize = 18.sp,
        color = Color.Black
    )
}

@Composable
fun AboutSectionImage(resource: Int, imageSize: Dp = 50.dp) {
    Image(
        painter = painterResource(id = resource),
        contentDescription = "",
        modifier = Modifier.size(imageSize)
    )
}