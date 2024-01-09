package com.upaep.upaeppersonal.view.features.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.genericcomponents.ModalListGeneric
import com.upaep.upaeppersonal.view.base.theme.Yellow_Schedule
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.schedule.ScheduleViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ScheduleScreen(scheduleViewModel: ScheduleViewModel = hiltViewModel()) {
    val userPreferences = UserPreferences(LocalContext.current)
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var selectedDay by remember { mutableStateOf("Lunes") }
    BaseViewWithModal(
        modalContent = {
            ModalListGeneric(onElementClick = { day ->
                scope.launch {
                    selectedDay = day.toString()
                    state.hide()
                }
            }, list = getDayList(), selected = selectedDay)
        }, content = {
            for (index in 0..3) {
                SingleClass(textColor = activeTheme!!.BASE_TEXT_COLOR)
            }
        },
        upperCardText = selectedDay,
        onUpperCardClick = {
            scope.launch { state.show() }
        },
        multipleElements = true,
        state = state, loading = false
    )
}

@Composable
fun SingleClass(textColor: Color) {
    Column(modifier = Modifier.padding(top = 10.dp)) {
        Text(
            text = "E-109 07:00-08:30",
            fontSize = 14.sp,
            fontFamily = roboto_regular,
            color = Yellow_Schedule
        )
        Text(
            text = "Desarrollo Web para móviles",
            color = textColor,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
            fontFamily = roboto_bold,
            fontSize = 12.sp
        )
        Text(
            text = "Presencial",
            color = textColor,
            modifier = Modifier.padding(start = 10.dp),
            fontFamily = roboto_regular,
            fontSize = 10.sp
        )
        Divider(modifier = Modifier.padding(top = 10.dp))
    }
}

fun getDayList(): List<String> {
    return listOf(
        "Lunes",
        "Martes",
        "Miercoles",
        "Jueves",
        "Viernes",
        "Sábado"
    )
}