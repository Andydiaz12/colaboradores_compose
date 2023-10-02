package com.upaep.upaeppersonal.view.features.assistance

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.assistance.RollcallStudent
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseViewWithModal
import com.upaep.upaeppersonal.view.base.genericcomponents.ModalListGeneric
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.view.base.theme.roboto_regular
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview(showSystemUi = true)
@Composable
fun AssistanceClassScreen() {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val activeTheme by
    UserPreferences(LocalContext.current).activeTheme.collectAsState(initial = defaultTheme)
    val dayList = getDates()
    var selectedDate by remember { mutableStateOf(dayList.first()) }
    BaseViewWithModal(modalContent = {
        ModalListGeneric(onElementClick = {
            scope.launch {
                selectedDate = it.toString()
                state.hide()
            }
        }, list = dayList)
    }, content = {
        ClassDescription(
            activeTheme = activeTheme!!,
            onRowClick = { scope.launch { state.show() } },
            selectedDate = selectedDate
        )
    }, state = state, loading = false)
}

@Composable
fun ClassDescription(activeTheme: ActiveTheme, onRowClick: () -> Unit, selectedDate: String) {
    Column {
        SingleClass(activeTheme = activeTheme)
        DateSelector(onRowClick = onRowClick, selectedDate = selectedDate)
        getStudents().forEach { student ->
            if (student.visible) SingleStudent(color = 1, name = student.name ?: "")
        }
        FooterSection()
    }
}

@Composable
fun FooterSection() {
    Column {
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            Image(
                painter = painterResource(id = R.drawable.icono_alumno_verde),
                contentDescription = "",
                modifier = Modifier
                    .height(20.dp)
                    .width(10.dp)
            )
            Text(
                text = "0",
                fontFamily = roboto_bold,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icono_alumno_rojo),
                contentDescription = "",
                modifier = Modifier
                    .height(20.dp)
                    .width(10.dp)
            )
            Text(
                text = "0",
                fontFamily = roboto_bold,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { }, modifier = Modifier.align(Alignment.Center)) {
                Text(text = "Guardar")
            }
        }
    }
}

@Composable
fun DateSelector(onRowClick: () -> Unit, selectedDate: String) {
    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .clickable(
                onClick = { onRowClick() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() })
    ) {
        Text(text = "Elige la fecha:", fontSize = 12.sp, modifier = Modifier.weight(1f))
        Text(
            text = selectedDate,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f),
            fontFamily = roboto_bold
        )
        Box(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(R.drawable.icono_pase_lista_fecha),
                contentDescription = "",
                modifier = Modifier.width(20.dp),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Composable
fun SingleStudent(color: Int, name: String) {
    val image = when (color) {
        1 -> R.drawable.icono_alumno_verde
        2 -> R.drawable.icono_alumno_rojo
        else -> R.drawable.icono_alumno_amarillo
    }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clickable(onClick = {},
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
        ) {
            Text(
                text = name,
                modifier = Modifier.weight(1f),
                fontFamily = roboto_regular
            )
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier
                    .height(20.dp)
                    .width(10.dp)
            )
        }
        Divider()
    }
}

fun getStudents(): List<RollcallStudent> {
    return listOf(
        RollcallStudent(id = 3359791, name = "María Rodríguez Pérez", visible = true),
        RollcallStudent(id = 3359792, name = "Juan Antonio García López", visible = true),
        RollcallStudent(id = 3359793, name = "Ana Martínez Sánchez", visible = true),
        RollcallStudent(id = 3359794, name = "Carlos José Fernández González", visible = true),
        RollcallStudent(id = 3359795, name = "Laura González Martín", visible = true),
        RollcallStudent(id = 3359796, name = "Javier Alejandro López Rodríguez", visible = true),
        RollcallStudent(id = 3359797, name = "Isabel Pérez García", visible = true),
        RollcallStudent(id = 3359798, name = "Pedro Luis Sánchez Fernández", visible = true),
    )
}

fun getDates(): List<String> {
    return listOf(
        "2023-09-15",
        "2023-09-14",
        "2023-09-13",
        "2023-09-12",
        "2023-09-11",
        "2023-09-10",
        "2023-09-09",
        "2023-09-08",
        "2023-09-07",
        "2023-09-06",
        "2023-09-05",
        "2023-09-04",
        "2023-09-03",
        "2023-09-02",
        "2023-09-01",
    )
}