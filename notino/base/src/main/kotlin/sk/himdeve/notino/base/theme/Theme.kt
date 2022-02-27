package sk.himdeve.notino.base.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

/**
 * Created by Robin Himdeve on 2/23/2022.
 */
@Composable
fun NotinoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = NotinoThemeColors,
        shapes = NotinoShapes,
        typography = NotinoTypography,
        content = content
    )
}

private val NotinoThemeColors = lightColors(
    primary = White,
    primaryVariant = White,
    onPrimary = Black_a30,
    secondary = Black_a30,
    secondaryVariant = Black_a30,
    onSecondary = White,
    surface = Grey_a10,
    onSurface = White_a70,
    error = Red
)