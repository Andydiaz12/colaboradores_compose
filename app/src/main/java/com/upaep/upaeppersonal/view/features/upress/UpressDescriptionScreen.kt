package com.upaep.upaeppersonal.view.features.upress

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.genericcomponents.HtmlContent
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.upress.UpressDescriptionViewModel

@Preview
@Composable
fun UpressDescriptionScreen(
    upressDescriptionViewModel: UpressDescriptionViewModel = hiltViewModel(),
    navigation: NavHostController? = null
) {
    val context = LocalContext.current
    val item by upressDescriptionViewModel.item.observeAsState()
    var fontSize by remember { mutableFloatStateOf(14f) }

    BaseView(
        navigation = navigation,
        screenName = "UPRESS",
        upperCardContent = {
            UpperHeaderContent(
                onLetterSizeChange = { fontSize = if (fontSize == 14f) 16f else 14f },
                shareUpress = { upressDescriptionViewModel.shareUpress(context = context) }
            )
        }
    ) {
        UpressContent(item = item, fontSize = fontSize)
    }
}

@Composable
fun UpperHeaderContent(onLetterSizeChange: () -> Unit, shareUpress: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        ImageConfig(image = R.drawable.icono_crecer_letra, onClick = onLetterSizeChange)
        Spacer(modifier = Modifier.size(5.dp))
        ImageConfig(image = R.drawable.icono_compartir, onClick = shareUpress)
    }
}

@Composable
fun ImageConfig(image: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = Modifier
            .size(20.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick() }
            )
    )
}

@Composable
fun UpressContent(item: UpressItem?, fontSize: Float) {
    val textContent = buildAnnotatedString {
        withStyle(style = SpanStyle(fontFamily = roboto_bold)) {
            append("Autor: ")
        }
        append(item?.author ?: "-----")
    }
    Column {
        AsyncImage(model = item?.imageFullText, contentDescription = null)
        Text(text = textContent, color = Color.Black, fontFamily = roboto_regular)
        HtmlContent(content = item?.introtext ?: "", fontSize = fontSize)
    }
}