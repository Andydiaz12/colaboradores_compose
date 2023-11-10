package com.upaep.upaeppersonal.view.features.dailymail

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.theme.roboto_regular

@Preview(showSystemUi = true)
@Composable
fun ElementsDescription() {
    BaseView(text = "Comunicados") {
        ContainerElements(element = emptyList())
    }
}

@Composable
fun ContainerElements(element: List<Any>) {
    element.forEach {
        SingleElement()
    }
}

@Preview(showSystemUi = true)
@Composable
fun SingleElement() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "UPAEP anuncia la creación de la Dirección de Evaluación y Gestión de la Calidad",
            modifier = Modifier.weight(1f),
            fontFamily = roboto_regular,
            fontSize = 14.sp
        )
        Icon(imageVector = Icons.Default.ArrowRight, contentDescription = null)
    }
}