package com.tesera.designsystem.theme

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
        heading1 = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 32.sp
                TeseraSize.Medium -> 36.sp
                TeseraSize.Big -> 40.sp
            },
            fontWeight = FontWeight.Bold
        ),
        heading4 = TextStyle(
            fontSize = when(textSize) {
                TeseraSize.Small -> 18.sp
                TeseraSize.Medium -> 22.sp
                TeseraSize.Big -> 26.sp
            }
        ),
        bodyMedium = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 14.sp
                TeseraSize.Medium -> 16.sp
                TeseraSize.Big -> 18.sp
            }
        ),
        bodyRegular = TextStyle(
            fontSize = when(textSize) {
                TeseraSize.Small -> 12.sp
                TeseraSize.Medium -> 14.sp
                TeseraSize.Big -> 16.sp
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