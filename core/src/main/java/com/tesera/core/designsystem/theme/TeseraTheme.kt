package com.tesera.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LocalColorProvider = staticCompositionLocalOf<Colors> {
    error("No default colors provided")
}

private val LocalTypographyProvider = staticCompositionLocalOf<TeseraTypography> {
    error("No default colors provided")
}

object AppTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColorProvider.current
    val typography: TeseraTypography
        @Composable
        get() = LocalTypographyProvider.current
}


@Composable
fun TeseraTheme(
    textSize: TeseraSize = TeseraSize.Medium,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!darkTheme) lightPalette else darkPalette

    val typography = TeseraTypography(
        heading = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 24.sp
                TeseraSize.Medium -> 28.sp
                TeseraSize.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 14.sp
                TeseraSize.Medium -> 16.sp
                TeseraSize.Big -> 18.sp
            }
        ),
        hint = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 8.sp
                TeseraSize.Medium -> 10.sp
                TeseraSize.Big -> 12.sp
            }
        ),
    )
    CompositionLocalProvider(
        LocalColorProvider provides colors,
        LocalTypographyProvider provides typography,
        content = content
    )
}