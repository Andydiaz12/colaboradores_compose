package com.upaep.upaeppersonal.view.features.directory

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.entities.features.directory.DirectoryPerson
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.genericcomponents.EmptyData
import com.upaep.upaeppersonal.view.base.theme.Arrow_color
import com.upaep.upaeppersonal.view.base.theme.Dark_grey
import com.upaep.upaeppersonal.view.base.theme.Gray_title

@Preview(showSystemUi = true)
@Composable
fun DirectoryScreen() {
    val directoryPeople = getPeople()
    val visibleDescription by remember {
        mutableStateOf(mutableStateListOf<Boolean>())
    }
    var searchValue by remember { mutableStateOf("") }

    repeat(directoryPeople.size) {
        visibleDescription.add(false)
    }

    BaseView(screenName = "DIRECTORIO") {
        DirectoryContent(
            directoryPeople = directoryPeople,
            onPersonClick = { index ->
                visibleDescription[index] = visibleDescription[index].let { !it }
            },
            visibleDescription = visibleDescription,
            value = searchValue,
            onValueChange = { newValue -> searchValue = newValue },
            resetValue = { searchValue = "" }
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
    resetValue: () -> Unit
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
                            Text(text = "Buscar")
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
        if (true) {
            EmptyData(message = "No se han encontrado datos")
        } else {
            directoryPeople.forEachIndexed { index, person ->
                SinglePersonDesc(
                    directoryPerson = person,
                    index = index,
                    onPersonClick = onPersonClick,
                    isVisible = visibleDescription[index]
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
    isVisible: Boolean
) {
    val currentRotation by remember { mutableStateOf(0f) }
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
            Text(text = directoryPerson.name ?: "", modifier = Modifier.weight(1f))
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
            PersonDescription(directoryPerson = directoryPerson)
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
fun PersonDescription(directoryPerson: DirectoryPerson) {
    Column {
        if (!directoryPerson.departament.isNullOrBlank()) {
            DescriptionContainer(
                image = R.drawable.icono_ubicacion_directorio,
                text = directoryPerson.departament!!
            )
        }
        if (!directoryPerson.phone.isNullOrBlank()) {
            DescriptionContainer(
                image = R.drawable.icono_telefono_directorio,
                text = directoryPerson.phone!!
            )
        }
        if (!directoryPerson.mail.isNullOrBlank()) {
            DescriptionContainer(
                image = R.drawable.icono_correo_directorio,
                text = directoryPerson.mail!!
            )
        }
    }
}

@Composable
fun DescriptionContainer(image: Int, text: String) {
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
        Text(text = text)
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