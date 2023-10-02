package com.upaep.upaeppersonal.view.features.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.calendar.CalendarConfiguration
import com.upaep.upaeppersonal.model.entities.features.calendar.CalendarOptions
import com.upaep.upaeppersonal.model.entities.features.calendar.GoogleEvents
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.genericcomponents.ModalListGeneric
import com.upaep.upaeppersonal.view.base.genericcomponents.SelectorCard
import com.upaep.upaeppersonal.view.base.theme.Background
import com.upaep.upaeppersonal.view.base.theme.Dark_grey
import com.upaep.upaeppersonal.view.base.theme.Upaep_grey
import com.upaep.upaeppersonal.view.base.theme.Upaep_red
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.calendar.CalendarViewModel
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CalendarScreen(calendarViewModel: CalendarViewModel = hiltViewModel()) {
    val activeTheme by UserPreferences(LocalContext.current).activeTheme.collectAsState(initial = defaultTheme)
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val daysWithEvents = emptyList<Int>()
    var selectedDay by rememberSaveable { mutableStateOf(calendarViewModel.getActualDay()) }
    val calendarOptions = getCalendarOptions()
    var calendarSelected by remember { mutableStateOf(calendarOptions.first()) }
    val calendarConfig by calendarViewModel.calendarConfiguration.observeAsState(calendarViewModel.getCalendarConfiguration())
    val actualMonth = calendarViewModel.getActualMonth()
    val actualDay = calendarViewModel.getActualDay()
    val actualYear = calendarViewModel.getActualYear()
    val events = emptyList<GoogleEvents>()
    BaseViewWithModal(
        modalContent = {
            ModalListGeneric(
                onElementClick = { value ->
                    scope.launch {
                        state.hide()
                        calendarSelected = value as CalendarOptions
                    }
                },
                list = getCalendarOptions(),
                obj = CalendarOptions::class,
                searchParam = "description",
                selected = calendarSelected
            )
        },
        content = {
            CalendarContent(
                actualDay = actualDay,
                actualMonth = actualMonth,
                actualYear = actualYear,
                calendarConfig = calendarConfig,
                calendarViewModel = calendarViewModel,
                daysWithEvents = daysWithEvents,
                events = events,
                selectedDay = selectedDay,
                clickedDay = { selection -> selectedDay = selection }
            )
        },
        state = state,
        loading = false,
        screenName = "CALENDARIOS",
        onUpperCardClick = { scope.launch { state.show() } },
        upperCardText = calendarSelected.description
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarContent(
    daysWithEvents: List<Int>,
    selectedDay: Int,
    actualMonth: Int,
    actualYear: Int,
    actualDay: Int,
    calendarConfig: CalendarConfiguration,
    events: List<GoogleEvents>,
    clickedDay: (Int) -> Unit,
    calendarViewModel: CalendarViewModel
) {
    CalendarContainerGeneral(
        daysWithEvents = daysWithEvents,
        clickedDay = clickedDay,
        selectedDay = selectedDay,
        actualYear = actualYear,
        actualDay = actualDay,
        actualMonth = actualMonth,
        calendarConfig = calendarConfig,
        events = events,
        getIfEventsInDay = calendarViewModel.getIfEventsInDay(selectedDay),
        calendarViewModel = calendarViewModel
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarContainerGeneral(
    events: List<GoogleEvents>,
    daysWithEvents: List<Int>,
    clickedDay: (Int) -> Unit,
    selectedDay: Int,
    actualDay: Int,
    actualYear: Int,
    actualMonth: Int,
    calendarConfig: CalendarConfiguration,
    getIfEventsInDay: Boolean,
    calendarViewModel: CalendarViewModel
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        CalendarContainer(
            daysWithEvents = daysWithEvents,
            clickedDay = clickedDay,
            selectedDay = selectedDay,
            actualYear = actualYear,
            actualDay = actualDay,
            actualMonth = actualMonth,
            calendarConfig = calendarConfig,
            getOtherMonth = { movement -> calendarViewModel.getOtherMonth(movement = movement) }
        )
        Divider(modifier = Modifier.padding(vertical = 25.dp))
        EventDescriptionContainer(
            events = events,
            selectedDay = selectedDay,
            getIfEventsInDay = getIfEventsInDay,
            calendarViewModel = calendarViewModel
        )
    }
}

@Composable
fun CalendarContainer(
    daysWithEvents: List<Int>,
    clickedDay: (Int) -> Unit,
    selectedDay: Int,
    calendarConfig: CalendarConfiguration,
    actualYear: Int,
    actualDay: Int,
    actualMonth: Int,
    getOtherMonth: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CalendarMonthContainer(
            monthName = calendarConfig.monthName,
            getOtherMonth = getOtherMonth,
            year = calendarConfig.yearInInt.toString()
        )
        Spacer(modifier = Modifier.size(20.dp))
        CalendarUpaep(
            startDay = calendarConfig.startDay,
            numRows = calendarConfig.numRows,
            daysMatrix = calendarConfig.daysMatrix,
            monthSelected = calendarConfig.monthNumber,
            actualMonth = actualMonth,
            actualDay = actualDay,
            daysWithEvents = daysWithEvents,
            clickedDay = clickedDay,
            selectedDay = selectedDay,
            selectedYear = calendarConfig.yearInInt,
            actualYear = actualYear
        )
    }
}

@Composable
fun CalendarMonthContainer(monthName: String, getOtherMonth: (String) -> Unit, year: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(50))
            .background(Background)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MonthMovement(
            modifier = Modifier.weight(1f),
            getOtherMonth = getOtherMonth,
            movement = "prev",
            movementDesc = "anterior"
        )
        MonthName(monthName = monthName, modifier = Modifier.weight(5f), year = year)
        MonthMovement(
            modifier = Modifier.weight(1f),
            movement = "next",
            movementDesc = "siguiente",
            getOtherMonth = getOtherMonth
        )
    }
}

@Composable
fun MonthName(monthName: String, modifier: Modifier, year: String) {
    Text(
        text = "$monthName $year",
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontFamily = roboto_bold,
        fontSize = 16.sp,
        color = Color.Black
    )
}

@Composable
fun MonthMovement(
    modifier: Modifier,
    movement: String,
    movementDesc: String,
    getOtherMonth: (String) -> Unit
) {
    Icon(
        modifier = modifier
            .size(30.dp)
            .rotate(degrees = if (movement == "prev") 180f else 0f)
            .clickable { getOtherMonth(movement) },
        imageVector = Icons.Default.ArrowRight,
        contentDescription = "mes $movementDesc",
        tint = Upaep_red
    )
}

@Composable
fun CalendarUpaep(
    startDay: Int,
    numRows: Int,
    daysMatrix: Array<IntArray>,
    actualMonth: Int,
    actualDay: Int,
    monthSelected: Int,
    daysWithEvents: List<Int>,
    clickedDay: (Int) -> Unit,
    selectedDay: Int,
    selectedYear: Int,
    actualYear: Int
) {
    Column {
        Row(Modifier.fillMaxWidth()) {
            for (i in 0 until 7) {
                val dayOfWeek = ((i + startDay - 1) % 7) + 1
                Text(
                    text = DateFormatSymbols.getInstance(
                        Locale(
                            "es",
                            "ES"
                        )
                    ).shortWeekdays[dayOfWeek].substring(0, 1).uppercase(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = Upaep_red,
                    fontFamily = roboto_bold
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        for (row in 0 until numRows) {
            Row(Modifier.fillMaxWidth()) {
                for (col in 0 until 7) {
                    val dayOfMonth = daysMatrix[row][col]
                    if (dayOfMonth != 0) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(
                                    if (selectedDay == dayOfMonth) Upaep_yellow
                                    else if (daysWithEvents.size > dayOfMonth && daysWithEvents[dayOfMonth] == 1) {
                                        Upaep_yellow
                                    } else Color.Transparent
                                )
                                .clickable { clickedDay(dayOfMonth) }
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = dayOfMonth.toString(),
                                    textAlign = TextAlign.Center,
                                    fontFamily = if ((actualDay == dayOfMonth && actualMonth == monthSelected && actualYear == selectedYear) || selectedDay == dayOfMonth || daysWithEvents.size > dayOfMonth && daysWithEvents[dayOfMonth] == 1) roboto_bold else roboto_regular,
                                    color =
                                    if (selectedDay == dayOfMonth) Color.White
                                    else if (actualDay == dayOfMonth && actualMonth == monthSelected && actualYear == selectedYear) Color.Black
                                    else if (daysWithEvents.size > dayOfMonth && daysWithEvents[dayOfMonth] == 1) Dark_grey
                                    else Dark_grey
                                )
                            }
                        }
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDescriptionContainer(
    events: List<GoogleEvents>,
    selectedDay: Int,
    getIfEventsInDay: Boolean,
    calendarViewModel: CalendarViewModel
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        if (!getIfEventsInDay) { //getIfEventsInDay
            Text(
                "EVENTOS",
                modifier = Modifier.padding(start = 8.dp),
                color = Upaep_red,
                fontFamily = roboto_bold
            )
        }
        events.forEach { event ->
            if (calendarViewModel.dayWithinRange(selectedDay = selectedDay, event = event)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EventDot(dotSize = 6.dp, modifier = Modifier.padding(top = 0.dp))
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = event.summary ?: "",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = roboto_bold
                    )
                }
            }
        }
        if (getIfEventsInDay) {
            NoneEvents()
        }
    }
}

@Composable
fun NoneEvents() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "No tienes eventos para este día",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(50.dp),
            color = Upaep_grey,
            fontFamily = roboto_regular,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EventDot(dotSize: Dp, modifier: Modifier = Modifier) {
    Surface(shape = RoundedCornerShape(50), modifier = modifier.size(dotSize), color = Color.Red) {}
}

fun getCalendarOptions(): List<CalendarOptions> {
    return listOf(
        CalendarOptions(id = 1, description = "PREPA PRESENCIAL"),
        CalendarOptions(id = 2, description = "LICENCIATURAS PRESENCIAL"),
        CalendarOptions(id = 3, description = "POSGRADOS PRESENCIAL"),
        CalendarOptions(id = 4, description = "PRIMARIA PRESENCIAL"),
        CalendarOptions(id = 5, description = "KINDER PRESENCIAL")
    )
}