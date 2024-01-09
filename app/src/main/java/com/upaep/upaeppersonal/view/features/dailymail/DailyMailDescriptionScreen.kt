package com.upaep.upaeppersonal.view.features.dailymail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.genericcomponents.HtmlContent
import com.upaep.upaeppersonal.viewmodel.features.dailymail.DailyMailDescriptionViewModel

@Composable
fun DailyMailDescriptionScreen(
    navigation: NavHostController,
    dailyMailDescriptionViewModel: DailyMailDescriptionViewModel = hiltViewModel()
) {
    val content = dailyMailDescriptionViewModel.screenContent
    val context = LocalContext.current
    val activeTheme by UserPreferences(context = context).activeTheme.collectAsState(initial = defaultTheme)
    val loadingScreen by dailyMailDescriptionViewModel.loadingScreen.observeAsState()
    BaseView(loadingScreen = loadingScreen!!, navigation = navigation, screenName = "CORREO DEL DÍA") {
        content.forEach { item ->
            Text(text = item.title, color = activeTheme!!.BASE_TEXT_COLOR)
            Spacer(modifier = Modifier.size(10.dp))
            HtmlContent(content = item.content!!, fontSize = 14f, withImage = true)
        }
    }
}