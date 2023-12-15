package com.upaep.upaeppersonal.view.features.directory

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.directory.DirectoryPerson
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.genericcomponents.EmptyData
import com.upaep.upaeppersonal.view.base.theme.Arrow_color
import com.upaep.upaeppersonal.view.base.theme.Dark_grey
import com.upaep.upaeppersonal.view.base.theme.Gray_title
import com.upaep.upaeppersonal.viewmodel.features.directory.DirectoryViewModel

@Preview(showSystemUi = true)
@Composable
fun DirectoryScreen(directoryViewModel: DirectoryViewModel = hiltViewModel()) {
    val activeTheme by UserPreferences(LocalContext.current).activeTheme.collectAsState(initial = defaultTheme)
    val directoryPeople = directoryViewModel.peopleList
    val visibleDescription = directoryViewModel.visibleDescription
    var searchValue by remember { mutableStateOf("") }

    BaseView(screenName = "DIRECTORIO") {
        DirectoryContent(
            directoryPeople = directoryPeople,
            onPersonClick = { index ->
                visibleDescription[index] = visibleDescription[index].let { !it }
            },
            visibleDescription = visibleDescription,
            value = searchValue,
            onValueChange = { newValue ->
                searchValue = newValue
                directoryViewModel.getData(searchValue)
            },
            resetValue = {
                searchValue = ""
                directoryViewModel.clearData()
            },
            theme = activeTheme!!
        )
    }
}

@Composable
fun DirectoryContent(
    directoryPeople: List<DirectoryPerson>,
    onPersonClick: (Int) -> Unit,
    visibleDescription: List<Boolean>,
    value: String,
    onValueChange: (String) -> Unit,
    resetValue: () -> Unit,
    theme: ActiveTheme
) {
    Column {
        BasicTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Gray_title,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (value.isNotEmpty()) {
                        Surface(
                            color = Dark_grey,
                            shape = CircleShape,
                            modifier = Modifier.clickable { resetValue() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp)
                    ) {
                        if (value.isEmpty()) {
                            Text(text = "Buscar", color = theme.BASE_TEXT_COLOR)
                        }
                        innerTextField()
                    }
                    Image(
                        painter = painterResource(id = R.drawable.icono_lupa),
                        contentDescription = ""
                    )
                }
            })
        Spacer(modifier = Modifier.size(10.dp))
        if (directoryPeople.isEmpty()) {
            EmptyData(message = "No se han encontrado datos", theme = theme)
        } else {
            directoryPeople.forEachIndexed { index, person ->
                SinglePersonDesc(
                    directoryPerson = person,
                    index = index,
                    onPersonClick = onPersonClick,
                    isVisible = visibleDescription[index],
                    theme = theme
                )
            }
        }
    }
}

@Composable
fun SinglePersonDesc(
    directoryPerson: DirectoryPerson,
    index: Int,
    onPersonClick: (Int) -> Unit,
    isVisible: Boolean,
    theme: ActiveTheme
) {
    val currentRotation by remember { mutableFloatStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .clickable { onPersonClick(index) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.icono_user_directorio),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp)
                    .size(14.dp)
            )
            Text(
                text = directoryPerson.name ?: "",
                modifier = Modifier.weight(1f),
                color = theme.BASE_TEXT_COLOR
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "",
                modifier = Modifier.rotate(rotation.value),
                tint = Arrow_color
            )
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            PersonDescription(directoryPerson = directoryPerson, theme = theme)
        }
        Divider()
        LaunchedEffect(isVisible) {
            rotation.animateTo(
                targetValue = if (isVisible) -180f else 0f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        }
    }
}

@Composable
fun PersonDescription(directoryPerson: DirectoryPerson, theme: ActiveTheme) {
    Column {
        if (!directoryPerson.departament.isNullOrBlank()) {
            DescriptionContainer(
                image = R.drawable.icono_ubicacion_directorio,
                text = directoryPerson.departament!!,
                theme = theme
            )
        }
        if (!directoryPerson.phone.isNullOrBlank()) {
            DescriptionContainer(
                image = R.drawable.icono_telefono_directorio,
                text = directoryPerson.phone!!,
                theme = theme
            )
        }
        if (!directoryPerson.mail.isNullOrBlank()) {
            DescriptionContainer(
                image = R.drawable.icono_correo_directorio,
                text = directoryPerson.mail!!,
                theme = theme
            )
        }
    }
}

@Composable
fun DescriptionContainer(image: Int, text: String, theme: ActiveTheme) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .size(15.dp)
        )
        Text(text = text, color = theme.BASE_TEXT_COLOR)
    }
}

fun getPeople(): List<DirectoryPerson> {
    return listOf(
        DirectoryPerson(
            name = "Nombre de colaborador",
            departament = "INNOVACION Y DESARROLLO DIGITAL",
            phone = "1234567890",
            mail = "correo@upaep.mx"
        ),
        DirectoryPerson(
            name = "Nombre de segundo",
            departament = "INNOVACION Y DESARROLLO DIGITAL",
            mail = "idd@upaep.mx"
        ),
        DirectoryPerson(
            name = "Lord valdomero",
            departament = "UPAEP MX"
        ),
        DirectoryPerson(
            name = "Harry Popotter",
            departament = "Grigrigriffindor",
            phone = "19191919"
        )
    )
}