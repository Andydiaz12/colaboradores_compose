package com.upaep.upaeppersonal.view.features.menu

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.menu.MenuElements
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.genericcomponents.Header
import com.upaep.upaeppersonal.view.base.theme.ThemeSchema
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun MenuScreen(navigation: NavHostController? = null) {
    val userPreferences = UserPreferences(LocalContext.current)
    val activeTheme by userPreferences.activeTheme.collectAsState(initial = defaultTheme)
    BaseView(transparentBackground = true, lazyPadding = 0.dp) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Title(textColor = activeTheme!!.BASE_TEXT_COLOR)
            Content(activeTheme = activeTheme!!, onThemeChange = {})
        }
    }
}

@Composable
fun Title(textColor: Color) {
    Column(modifier = Modifier) {
        Text(text = "MENÚ", color = textColor, fontFamily = roboto_bold)
        Spacer(modifier = Modifier.size(8.dp))
        Divider(color = Upaep_yellow)
    }
}

@Composable
fun Content(activeTheme: ActiveTheme, onThemeChange: (ThemeSchema) -> Unit) {
    val validateTheme = ThemeSchema.getActiveTheme(activeTheme) is ThemeSchema.DARK
    Column(modifier = Modifier.padding(horizontal = 25.dp)) {
        Spacer(modifier = Modifier.size(15.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Tema", color = activeTheme.BASE_TEXT_COLOR)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Claro", color = activeTheme.BASE_TEXT_COLOR)
            Switch(
                checked = validateTheme,
                onCheckedChange = {
                    onThemeChange(
                        if (validateTheme) {
                            ThemeSchema.LIGHT
                        } else {
                            ThemeSchema.DARK
                        }
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Upaep_yellow,
                    checkedTrackColor = Color.Green,
                    uncheckedThumbColor = Color.Blue,
                    uncheckedTrackColor = Color(0xFFA2A4A0),
                    uncheckedBorderColor = Color.Transparent
                ),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Text(text = "Oscuro", color = activeTheme.BASE_TEXT_COLOR)
        }
        for (element in ContentOptions()) {
            Text(text = element.name, color = activeTheme.BASE_TEXT_COLOR)
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Preview
@Composable
fun AnimatedSwitch() {
    var alignment by remember { mutableStateOf(Alignment.CenterStart) }
    val alignmentAnimatable = remember { Animatable(0f) }
    val transition = updateTransition(targetState = alignment)

    LaunchedEffect(alignment) {
        alignmentAnimatable.animateTo(
            targetValue = when(alignment) {
                Alignment.CenterStart -> 0f
                Alignment.CenterEnd -> 1f
                else -> 0f
            }
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color.Magenta)
            .width(150.dp)
            .height(50.dp)
            .clickable {
                alignment = when (alignment) {
                    Alignment.CenterStart -> Alignment.CenterEnd
                    Alignment.CenterEnd -> Alignment.CenterStart
                    else -> Alignment.CenterStart
                }
            }
    ) {
        Surface(
            modifier = Modifier
                .size(50.dp)
                .padding(5.dp)
                .clip(CircleShape)
                .align(Alignment.CenterEnd)
        ) {

        }
    }
}

fun ContentOptions(): List<MenuElements> {
    return listOf(
        MenuElements(name = "Acerca de esta app", action = {}),
        MenuElements(name = "Aviso de privacidad", action = {}),
        MenuElements(name = "Reporta un problema", action = {}),
        MenuElements(name = "Recomienda la app", action = {}),
        MenuElements(name = "Normatividad para colaboradores", action = {}),
        MenuElements(name = "Manual de seguridad interna", action = {}),
        MenuElements(name = "Cerrar sesión", action = {})
    )
}