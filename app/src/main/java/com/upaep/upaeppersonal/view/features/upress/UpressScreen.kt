package com.upaep.upaeppersonal.view.features.upress

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.upaep.upaeppersonal.model.entities.features.upress.UpressOptions
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.genericcomponents.ModalListGeneric
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
fun UpressScreen() {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val upressOptionList = getUpressOptions()
    var selectedOption by remember { mutableStateOf(upressOptionList.first()) }
    val scope = rememberCoroutineScope()
    BaseViewWithModal(
        content = { },
        state = state,
        loading = false,
        upperCardText = selectedOption.title,
        onUpperCardClick = { scope.launch { state.show() } },
        modalContent = {
            ModalListGeneric(
                onElementClick = { element ->
                    scope.launch {
                        selectedOption = element as UpressOptions
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