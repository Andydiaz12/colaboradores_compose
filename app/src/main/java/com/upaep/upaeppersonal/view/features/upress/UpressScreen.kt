package com.upaep.upaeppersonal.view.features.upress

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.model.entities.features.upress.UpressOptions
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.genericcomponents.ModalListGeneric
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.upress.UpressViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpressScreen(
    upressViewModel: UpressViewModel = hiltViewModel(),
    navigation: NavHostController? = null
) {
    val activeTheme by UserPreferences(LocalContext.current).activeTheme.collectAsState(initial = defaultTheme)
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val upressOptionList = upressViewModel.upressOptionList
    val selectedOption by upressViewModel.selectedOption.observeAsState()
    val scope = rememberCoroutineScope()
    val upressList = upressViewModel.upressElements
    BaseViewWithModal(
        content = {
            UpressContent(
                upressList = upressList,
                textColor = activeTheme!!.BASE_TEXT_COLOR,
                dividerColor = activeTheme!!.LIST_DIVIDER_COLOR,
                onElementClick = { item ->
                    upressViewModel.navigateDescScreen(
                        item = item,
                        navigation = navigation
                    )
                }
            )
        },
        state = state,
        loading = false,
        upperCardText = selectedOption?.title ?: "",
        onUpperCardClick = { scope.launch { state.show() } },
        modalContent = {
            ModalListGeneric(
                onElementClick = { element ->
                    scope.launch {
                        upressViewModel.changeSelectedOption(element = element as UpressOptions)
                        state.hide()
                    }
                },
                list = upressOptionList,
                obj = UpressOptions::class.java,
                searchParam = "title",
                selected = selectedOption
            )
        }
    )
}

@Composable
fun UpressContent(
    upressList: List<UpressItem>,
    textColor: Color,
    dividerColor: Color,
    onElementClick: (UpressItem) -> Unit
) {
    upressList.forEachIndexed { index, element ->
        Column {
            SingleUpressNew(
                upressElement = element,
                textColor = textColor,
                onElementClick = onElementClick
            )
            if (index + 1 < upressList.size) Divider(
                color = dividerColor,
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
            )
        }
    }
}

@Composable
fun SingleUpressNew(
    textColor: Color,
    upressElement: UpressItem,
    onElementClick: (UpressItem) -> Unit
) {
    Row(modifier = Modifier.clickable { onElementClick(upressElement) }) {
        Column(
            modifier = Modifier
                .weight(7f)
                .height(120.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = upressElement.title ?: "",
                fontFamily = roboto_bold,
                fontSize = 14.sp,
                color = textColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = upressElement.publish ?: "",
                fontFamily = roboto_regular,
                fontSize = 14.sp,
                color = textColor
            )
        }
        if (!upressElement.imageIntro.isNullOrEmpty()) {
            SubcomposeAsyncImage(
                model = upressElement.imageIntro,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.weight(2f)
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator()
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}

fun getUpressOptions(): List<UpressOptions> {
    return listOf(
        UpressOptions(id = 1, title = "Investigación"),
        UpressOptions(id = 2, title = "Volamos alto"),
        UpressOptions(id = 3, title = "Arte y cultura"),
        UpressOptions(id = 4, title = "Experiencia global"),
        UpressOptions(id = 5, title = "Academia"),
        UpressOptions(id = 6, title = "Vida Universitaria"),
        UpressOptions(id = 7, title = "Excelencia UPAEP"),
        UpressOptions(id = 8, title = "Somos águilas de corazón"),
        UpressOptions(id = 9, title = "Campus y Comunidad"),
        UpressOptions(id = 10, title = "Pensamiento UPAEP"),
    )
}