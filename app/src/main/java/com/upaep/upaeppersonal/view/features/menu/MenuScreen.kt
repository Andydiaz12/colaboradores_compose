package com.upaep.upaeppersonal.view.features.menu

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.features.menu.MenuElements
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.genericcomponents.BaseView
import com.upaep.upaeppersonal.view.base.theme.ThemeSchema
import com.upaep.upaeppersonal.view.base.theme.Upaep_yellow
import com.upaep.upaeppersonal.view.base.theme.roboto_bold
import com.upaep.upaeppersonal.viewmodel.features.menu.MenuViewModel
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun MenuScreen(
    navigation: NavHostController? = null,
    menuViewModel: MenuViewModel = hiltViewModel()
) {
    val userPreferences = UserPreferences(LocalContext.current)
    val activeTheme by userPreferences.activeTheme.collectAsState(initial = defaultTheme)
    val contentOptions = menuViewModel.contentOptions
    val scope = rememberCoroutineScope()
    var selectedTheme =
        if (ThemeSchema.getActiveTheme(activeTheme!!) is ThemeSchema.DARK) 1f else -1f
    BaseView(transparentBackground = true, lazyPadding = 0.dp, rightIcon = false) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.size(20.dp))
            Title(textColor = activeTheme!!.BASE_TEXT_COLOR)
            Content(
                activeTheme = activeTheme!!,
                onThemeChange = {
                    scope.launch {
                        selectedTheme *= -1
                        userPreferences.setTheme(if (selectedTheme == 1f) ThemeSchema.DARK else ThemeSchema.LIGHT)
                    }
                },
                selectedTheme = selectedTheme,
                contentOptions = contentOptions,
                navigation = navigation
            )
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
fun Content(
    activeTheme: ActiveTheme,
    onThemeChange: () -> Unit,
    selectedTheme: Float,
    contentOptions: List<MenuElements>,
    navigation: NavHostController?
) {
    Column(modifier = Modifier.padding(horizontal = 25.dp)) {
        Spacer(modifier = Modifier.size(15.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Tema", color = activeTheme.BASE_TEXT_COLOR)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Claro", color = activeTheme.BASE_TEXT_COLOR)
            Spacer(modifier = Modifier.size(15.dp))
            AnimatedSwitch(selectedTheme = selectedTheme, onThemeChange = onThemeChange)
            Spacer(modifier = Modifier.size(15.dp))
            Text(text = "Oscuro", color = activeTheme.BASE_TEXT_COLOR)
        }
        Spacer(modifier = Modifier.size(20.dp))
        for (element in contentOptions) {
            Text(
                text = element.name,
                color = activeTheme.BASE_TEXT_COLOR,
                modifier = Modifier.clickable(
                    onClick = { element.action(navigation) },
                    interactionSource = MutableInteractionSource(),
                    indication = null
                )
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun AnimatedSwitch(selectedTheme: Float, onThemeChange: () -> Unit) {
//    var horizontalBias by remember { mutableStateOf(-1f) }
//    val alignment by animateHorizontalAlignmentAsState(horizontalBias)
    val alignment by animateHorizontalAlignmentAsState(selectedTheme)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color.Magenta)
            .width(40.dp)
            .height(25.dp)
            .clickable {
                onThemeChange()
            },
        horizontalAlignment = alignment
    ) {
        Surface(
            modifier = Modifier
                .size(25.dp)
                .padding(4.dp)
                .clip(CircleShape)
                .align(alignment)
        ) {

        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun animateHorizontalAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment.Horizontal> {
    val bias by animateFloatAsState(targetBiasValue, label = "")
    return derivedStateOf { BiasAlignment.Horizontal(bias) }
}