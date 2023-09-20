package com.upaep.upaeppersonal.view.features.assistance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.theme.Gray_title
import com.upaep.upaeppersonal.view.base.theme.Red_Upaep_Splash
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.viewmodel.features.assistance.AssistanceViewModel

@Preview(showSystemUi = true)
@Composable
fun AssistanceScreen(assistanceViewModel: AssistanceViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    val loading by assistanceViewModel.loading.collectAsState()
    BaseView(onRefresh = { assistanceViewModel.initialLoad() }, loading = loading, text = "Selecciona una asignatura") {
        repeat(16) {
            SingleClass(activeTheme = activeTheme!!)
        }
    }
}

@Composable
fun SingleClass(activeTheme: ActiveTheme) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "DESARROLLO WEB PARA MÓVILES",
            color = activeTheme.BASE_TEXT_COLOR,
            fontFamily = roboto_bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "CLAVE",
                    color = Red_Upaep_Splash,
                    fontFamily = roboto_bold,
                    fontSize = 12.sp
                )
                Text(
                    text = "ISW228",
                    color = activeTheme.BASE_TEXT_COLOR,
                    fontSize = 12.sp,
                    fontFamily = roboto_bold,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = "HORARIO",
                    color = Red_Upaep_Splash,
                    fontFamily = roboto_bold,
                    fontSize = 12.sp
                )
                Text(
                    text = "5-109 07:00-8:30 Ma J",
                    color = activeTheme.BASE_TEXT_COLOR,
                    fontSize = 12.sp,
                    fontFamily = roboto_bold,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "GRUPO",
                    color = Red_Upaep_Splash,
                    fontFamily = roboto_bold,
                    fontSize = 12.sp
                )
                Text(
                    text = "1",
                    color = activeTheme.BASE_TEXT_COLOR,
                    fontSize = 12.sp,
                    fontFamily = roboto_bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Divider(color = Gray_title, modifier = Modifier.padding(vertical = 15.dp))
    }
}