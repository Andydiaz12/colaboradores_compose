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
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.theme.Yellow_Schedule
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ScheduleScreen() {
    val userPreferences = UserPreferences(LocalContext.current)
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var selectedDay by remember { mutableStateOf("Lunes") }
    BaseViewWithModal(modalContent = {
        ModalContent(onDaySelect = { day ->
            scope.launch {
                selectedDay = day
                state.hide()
            }
        })
    }, content = {
        for (index in 0..3) {
            SingleClass(textColor = activeTheme!!.BASE_TEXT_COLOR)
        }
    }, cardHeader = {
        CardScheduleHeader(selectedDay = selectedDay) {
            scope.launch { state.show() }
        }
    }, state = state, loading = false)
}

@Composable
fun ModalContent(onDaySelect: (String) -> Unit) {
    val days = getDayList()
    Column {
        Spacer(modifier = Modifier.size(10.dp))
        days.forEachIndexed { index, day ->
            Text(
                text = day,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDaySelect(day) },
                textAlign = TextAlign.Center
            )
            if (index + 1 < days.size) {
                Divider(modifier = Modifier.padding(10.dp))
            } else {
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
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

@Composable
fun CardScheduleHeader(selectedDay: String, onCardClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Yellow_Schedule)
            .clickable { onCardClick() }
            .padding(12.dp)
    ) {
        Text(text = selectedDay, color = Color.White, fontSize = 12.sp, fontFamily = roboto_regular)
        Spacer(modifier = Modifier.weight(1f))
        Image(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White)
        )
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