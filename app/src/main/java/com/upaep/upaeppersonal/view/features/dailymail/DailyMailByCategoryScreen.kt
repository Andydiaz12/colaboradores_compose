package com.upaep.upaeppersonal.view.features.dailymail

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailItem
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.genericcomponents.HtmlContent
import com.upaep.upaeppersonal.view.base.theme.Upaep_red
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.dailymail.DailyMailByCategoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
fun DailyMailByCategoryScreen(
    navigation: NavHostController? = null,
    dailyMailByCategoryViewModel: DailyMailByCategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activeTheme by UserPreferences(context).activeTheme.collectAsState(initial = defaultTheme)
    val error by dailyMailByCategoryViewModel.error.observeAsState()
    val items = dailyMailByCategoryViewModel.items

    BaseView(
        content = {
            items.forEachIndexed { index, item ->
                Spacer(Modifier.size(10.dp))
                SingleElement(
                    item = item,
                    textColor = activeTheme!!.BASE_TEXT_COLOR,
                    onClick = { selectedItem ->
                        dailyMailByCategoryViewModel.getItemDesc(item = selectedItem, navigation = navigation)
                    })
                Spacer(Modifier.size(10.dp))
                if (index + 1 < items.size) Divider()
            }
        },
        loading = false,
        error = error,
        onCloseDialog = { dailyMailByCategoryViewModel.closeDialog() }
    )
}

@Composable
fun SingleElement(item: DailyMailItem, textColor: Color, onClick: (DailyMailItem) -> Unit) {
    Spacer(modifier = Modifier.size(5.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(
            interactionSource = MutableInteractionSource(),
            indication = null,
            onClick = { onClick(item) })
    ) {
        Text(
            text = item.title,
            modifier = Modifier.weight(1f),
            fontFamily = roboto_regular,
            fontSize = 14.sp,
            color = textColor
        )
        Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null, tint = Upaep_red)
    }
}