package com.upaep.upaeppersonal.view.base.theme

import androidx.compose.ui.graphics.Color
import com.upaep.upaeppersonal.R
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme

sealed class ThemeSchema(
    var BASE_TEXT_COLOR: Color,
    val BASE_BACKGROUND_IMAGE: Int,
    val HEADER_SCREEN_NAME_COLOR: Color,
    val HEADER_MENU_ICON: Int,
    val HEADER_BACK_ICON: Int,
    val BASE_BACKGROUND_COLOR: Color,
    val LIBRARY_ICON: Int
) {

    object LIGHT : ThemeSchema(
        BASE_TEXT_COLOR = Color.Black,
        BASE_BACKGROUND_IMAGE = R.drawable.fondo_blanco,
        HEADER_SCREEN_NAME_COLOR = Color.Black,
        HEADER_MENU_ICON = R.drawable.icono_menu_dark,
        HEADER_BACK_ICON = R.drawable.icono_back_dark,
        BASE_BACKGROUND_COLOR = Color.White,
        LIBRARY_ICON = R.drawable.icono_biblioteca_light
    )

    object DARK : ThemeSchema(
        BASE_TEXT_COLOR = Color.White,
        BASE_BACKGROUND_IMAGE = R.drawable.fondo_obscuro,
        HEADER_SCREEN_NAME_COLOR = Color.White,
        HEADER_MENU_ICON = R.drawable.icono_menu_light,
        HEADER_BACK_ICON = R.drawable.icono_back_light,
        BASE_BACKGROUND_COLOR = Color.Black,
        LIBRARY_ICON = R.drawable.icono_biblioteca_dark
    )

    companion object {
        fun getActiveTheme(activeTheme: ActiveTheme): ThemeSchema? {
            return when (activeTheme) {
                ActiveTheme(
                    Color.Black,
                    R.drawable.fondo_blanco,
                    Color.Black,
                    R.drawable.icono_menu_dark,
                    R.drawable.icono_back_dark,
                    Color.White,
                    R.drawable.icono_biblioteca_light
                ) -> LIGHT

                ActiveTheme(
                    Color.White,
                    R.drawable.fondo_obscuro,
                    Color.White,
                    R.drawable.icono_menu_light,
                    R.drawable.icono_back_light,
                    Color.Black,
                    R.drawable.icono_biblioteca_dark
                ) -> DARK

                else -> {
                    null
                }
            }
        }
    }
}
