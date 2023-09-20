package com.upaep.upaeppersonal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.view.base.defaultvalues.defaultTheme
import com.upaep.upaeppersonal.view.base.navigation.AppNavigation
import com.upaep.upaeppersonal.view.base.theme.ColaboradoresTheme
import com.upaep.upaeppersonal.view.base.theme.ThemeSchema
import com.upaep.upaeppersonal.viewmodel.base.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        val mainActivityViewModel: MainActivityViewModel by viewModels()
        var showSplash = true
        splashScreen.setKeepOnScreenCondition { showSplash }
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val userPreferences = UserPreferences(context)
            val activeTheme = mainActivityViewModel.activeTheme.collectAsState(initial = null).value
            Log.i("activeThemeDebug", activeTheme.toString())
            val selectedTheme =
                userPreferences.selectedTheme.collectAsState(initial = null).value
            if (selectedTheme != null) {
                if (!selectedTheme) {
                    mainActivityViewModel.setTheme(activeTheme = if (isSystemInDarkTheme()) ThemeSchema.DARK else ThemeSchema.LIGHT)
                }
                if (activeTheme != null) {
                    ColaboradoresTheme {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .paint(
                                    painterResource(id = activeTheme.BASE_BACKGROUND_IMAGE),
                                    contentScale = ContentScale.FillBounds
                                )
                        ) {
                            AppNavigation()
                        }
                    }
                }
            }
        }
        showSplash = false
    }
}