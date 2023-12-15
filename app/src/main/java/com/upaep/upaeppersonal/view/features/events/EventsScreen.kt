package com.upaep.upaeppersonal.view.features.events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.events.EventsResponse
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.theme.Green_events
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import com.upaep.upaeppersonal.viewmodel.features.events.EventsViewModel

@Preview(showSystemUi = true)
@Composable
fun EventsScreen(eventsViewModel: EventsViewModel = hiltViewModel()) {
    val activeTheme by UserPreferences(LocalContext.current).activeTheme.collectAsState(initial = defaultTheme)
    val loadingScreen by eventsViewModel.loadingScreen.observeAsState()
    val events = eventsViewModel.events
    BaseView(
        screenName = "REGISTRO DE EVENTOS",
        upperCardText = "HOY",
        cardPickerColor = Green_events,
        loadingScreen = loadingScreen!!,
        noData = events.isEmpty()
    ) {
        events.forEachIndexed { index, event ->
            SingleEventDescription(textColor = activeTheme!!.BASE_TEXT_COLOR, event = event)
            if (index == events.lastIndex) { Divider() }
        }
    }
}

@Composable
fun SingleEventDescription(textColor: Color, event: EventsResponse) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .padding(vertical = 5.dp)
            .clickable { }) {
        Text(text = event.activityName ?: "", fontFamily = roboto_bold, color = textColor)
        Text(text = "", fontFamily = roboto_regular, color = textColor)
        Text(text = event.activityStartTime ?: "-", color = Green_events, fontFamily = roboto_regular)
    }
}