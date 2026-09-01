package com.vaddshah2626.vetted.ui.theme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

// ? Text colors
val textPrimaryLight = Color(0xFF060606)
val textPrimaryDark = Color(0xFFDEDEDE)
val textSecondaryLight = Color(0xFF484848)
val textSecondaryDark = Color(0xFF9E9E9E)
val textTertiaryLight = Color(0xFF808080)
val textTertiaryDark = Color(0xFF636363)

@Immutable
data class ExtendedTextColors(
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color
)

val LocalExtendedTextColors = staticCompositionLocalOf {
    ExtendedTextColors(
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        textTertiary = Color.Unspecified,
    )
}

val LightExtendedTextColors = ExtendedTextColors(
    textPrimary = textPrimaryLight,
    textSecondary = textSecondaryLight,
    textTertiary = textTertiaryLight
)

val DarkExtendedTextColors = ExtendedTextColors(
    textPrimary = textPrimaryDark,
    textSecondary = textSecondaryDark,
    textTertiary = textTertiaryDark
)

object TextTheme {
    val colors: ExtendedTextColors
        @Composable
        get() = LocalExtendedTextColors.current
}