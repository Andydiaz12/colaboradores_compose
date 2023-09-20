package com.upaep.upaeppersonal.view.base.genericcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.navigation.Routes
import com.upaep.upaeppersonal.view.base.theme.roboto_bold

@Preview(showSystemUi = true)
@Composable
fun Header(
    modifier: Modifier = Modifier,
    backBtn: Boolean = true,
    notificationIcon: Boolean = false,
    screenName: String? = null,
    visibleLogo: Boolean = false,
    rightIcon: Boolean = true,
    navigation: NavHostController? = null,
    backBtnExtraAction: Boolean = false,
    backBtnExtraActionBtn: () -> Unit = {}
) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val activeTheme = userPreferences.activeTheme.collectAsState(initial = defaultTheme).value
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(25.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            LeftElements(
                backBtn = backBtn,
                notificationIcon = notificationIcon,
                modifier = Modifier.weight(1f),
                activeTheme = activeTheme!!,
                backButton = { navigation?.popBackStack() }
            )
            CenterElements(
                visibleLogo = visibleLogo,
                screenName = screenName,
                modifier = Modifier.weight(3f),
                screenNameColor = activeTheme.HEADER_SCREEN_NAME_COLOR
            )
            RightElements(
                activeTheme = activeTheme,
                modifier = Modifier.weight(1f),
                rightIcon = rightIcon,
                showMenu = {
                    navigation?.navigate(Routes.MenuScreen.routes) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun LeftElements(
    modifier: Modifier,
    backBtn: Boolean,
    notificationIcon: Boolean,
    activeTheme: ActiveTheme,
    backButton: () -> Unit
) {
    if (notificationIcon) {
        Image(
            painter = painterResource(id = R.drawable.icono_notificacion),
            contentDescription = "",
            modifier = modifier.size(20.dp)
        )
    } else if (backBtn) {
        Image(
            painter = painterResource(id = activeTheme.HEADER_BACK_ICON),
            contentDescription = "",
            modifier = modifier
                .size(20.dp)
                .clickable { backButton() }
        )
    } else {
        Spacer(modifier = modifier)
    }
}

@Composable
fun CenterElements(
    visibleLogo: Boolean,
    screenName: String?,
    modifier: Modifier,
    screenNameColor: Color
) {
    if (visibleLogo) {
        Image(
            painter = painterResource(id = R.drawable.logo_upaep),
            contentDescription = "",
            modifier = modifier.size(40.dp)
        )
    } else {
        Text(
            text = screenName ?: "",
            modifier = modifier,
            textAlign = TextAlign.Center,
            color = screenNameColor,
            fontFamily = roboto_bold
        )
    }
}

@Composable
fun RightElements(
    activeTheme: ActiveTheme,
    modifier: Modifier,
    rightIcon: Boolean,
    showMenu: () -> Unit
) {
    if (rightIcon) {
        Image(
            painter = painterResource(id = activeTheme.HEADER_MENU_ICON),
            contentDescription = "",
            modifier = modifier
                .size(18.dp)
                .clickable { showMenu() }
        )
    } else {
        Spacer(modifier = modifier)
    }
}