package com.upaep.upaeppersonal.view.features.benefits

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.theme.roboto_bold

@Preview(showSystemUi = true)
@Composable
fun BenefitsScreen() {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    BaseView(
        onRefresh = {},
        loading = false
    ) {
        getBenefits().forEachIndexed { index, benefit ->
            Spacer(modifier = Modifier.size(20.dp))
            IndividualBenefit(name = benefit.name, textColor = activeTheme!!.BASE_TEXT_COLOR)
            Spacer(modifier = Modifier.size(5.dp))
            if (index + 1 < getBenefits().size) Divider()
        }
    }
}

@Composable
fun IndividualBenefit(name: String, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            color = textColor,
            fontFamily = roboto_bold,
            fontSize = 14.sp
        )
        Icon(
            imageVector = Icons.Default.BrokenImage,
            contentDescription = "",
            modifier = Modifier.size(45.dp)
        )
    }
}

fun getBenefits(): List<Benefits> {
    return listOf(
        Benefits(name = "Todos"),
        Benefits(name = "Belleza y tiendas de moda"),
        Benefits(name = "Escuelas y guarderias"),
        Benefits(name = "Alimentos y restaurantes"),
        Benefits(name = "Servicios Profesionales"),
        Benefits(name = "Librerías"),
        Benefits(name = "Deportes y entretenimiento"),
        Benefits(name = "Viajes y hoteles"),
        Benefits(name = "Vehículos y Servicios Automotrices"),
        Benefits(name = "Hogar"),
    )
}

data class Benefits(
    val name: String
)