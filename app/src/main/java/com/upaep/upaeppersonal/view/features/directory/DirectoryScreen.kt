package com.upaep.upaeppersonal.view.features.directory

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.entities.features.directory.DirectoryPerson
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView

@Preview(showSystemUi = true)
@Composable
fun DirectoryScreen() {
    val directoryPeople = getPeople()
    val visibleDescription by remember {
        mutableStateOf(MutableList(directoryPeople.size) { false })
    }
    BaseView(screenName = "DIRECTORIO") {
        DirectoryContent(
            directoryPeople = directoryPeople,
            onPersonClick = { index ->
                Log.i("debugClicked_1", index.toString())
                Log.i("debugClicked_2", visibleDescription[index].toString())
                visibleDescription[index] = !visibleDescription[index]
                Log.i("debugClicked_3", visibleDescription[index].toString())
            },
            visibleDescription = visibleDescription
        )
    }
}

@Composable
fun DirectoryContent(
    directoryPeople: List<DirectoryPerson>,
    onPersonClick: (Int) -> Unit,
    visibleDescription: List<Boolean>
) {
    Column() {
        //INPUT
        Log.i("debugClicked_4", visibleDescription.toString())
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

@Composable
fun SinglePersonDesc(
    directoryPerson: DirectoryPerson,
    index: Int,
    onPersonClick: (Int) -> Unit,
    isVisible: Boolean
) {
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
            Icon(imageVector = Icons.Default.ArrowRight, contentDescription = "")
        }
        Divider()
        if (isVisible) PersonDescription(directoryPerson = directoryPerson)
    }
}

@Composable
fun PersonDescription(directoryPerson: DirectoryPerson) {
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
    Divider()
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