package org.maddiesoftware.komagareader.core.presentation.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle

@SuppressLint("ConflictingOnColor")
private val LightThemeColors = lightColors(
    primary = Color(0xFF546ee5),
    primaryVariant = Color(0xFF8c9cff),
    onPrimary = White,
    secondary = Color(0xFF46807E),
    secondaryVariant = Color(0xFF75b0ad),
    onSecondary = Black1,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = White,
    onBackground = White,
    surface = White,
    onSurface = Gray300,
)

private val DarkThemeColors = darkColors(
    primary = Color(0xFF546ee5),
    primaryVariant = Color(0xFF8c9cff),
    onPrimary = White,
    secondary = Color(0xFF46807E),
    secondaryVariant = Color(0xFF75b0ad),
    error = RedErrorLight,
    background = Black2,
    onBackground = White,
    surface = Black3,
    onSurface = White,
    onSecondary = White,

)

@Composable
fun KomagaReaderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = Typography,
        shapes = AppShapes,
        content = {
            ProvideTextStyle(
                value = TextStyle(color = White),
                content = content
            )
        }

    )
}