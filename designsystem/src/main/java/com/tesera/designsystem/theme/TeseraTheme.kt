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
    error("No default typography provided")
}

private val LocalPaddingProvider = staticCompositionLocalOf<TeseraPadding> {
    error("No default paddings provided")
}

private val LocalComponentSizeProvider = staticCompositionLocalOf<TeseraComponentSize> {
    error("No default component size provided")
}

private val LocalShapesProvider = staticCompositionLocalOf<TeseraShapes> {
    error("No default shapes provided")
}

object AppTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColorProvider.current
    val typography: TeseraTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypographyProvider.current

    val padding: TeseraPadding
        @Composable
        @ReadOnlyComposable
        get() = LocalPaddingProvider.current

    val sizes: TeseraComponentSize
        @Composable
        @ReadOnlyComposable
        get() = LocalComponentSizeProvider.current

    val shapes: TeseraShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapesProvider.current
}


@Composable
fun TeseraTheme(
    textSize: TeseraSize = TeseraSize.Medium,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (!darkTheme) lightPalette else darkPalette

    val typography = TeseraTypography(
        heading1 = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 88.sp
                TeseraSize.Medium -> 92.sp
                TeseraSize.Big -> 96.sp
            },
            fontWeight = FontWeight.Bold
        ),
        heading2 = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 52.sp
                TeseraSize.Medium -> 56.sp
                TeseraSize.Big -> 60.sp
            },
            fontWeight = FontWeight.Bold
        ),
        heading3 = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 40.sp
                TeseraSize.Medium -> 44.sp
                TeseraSize.Big -> 48.sp
            },
            fontWeight = FontWeight.Bold
        ),
        heading4 = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 26.sp
                TeseraSize.Medium -> 30.sp
                TeseraSize.Big -> 34.sp
            }
        ),
        heading5 = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 16.sp
                TeseraSize.Medium -> 20.sp
                TeseraSize.Big -> 24.sp
            }
        ),
        heading6 = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 12.sp
                TeseraSize.Medium -> 16.sp
                TeseraSize.Big -> 20.sp
            }
        ),
        body1 = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 12.sp
                TeseraSize.Medium -> 14.sp
                TeseraSize.Big -> 16.sp
            }
        ),
        body2 = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 10.sp
                TeseraSize.Medium -> 12.sp
                TeseraSize.Big -> 14.sp
            }
        ),
        bodySmall = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 8.sp
                TeseraSize.Medium -> 10.sp
                TeseraSize.Big -> 12.sp
            }
        ),
        hint = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 8.sp
                TeseraSize.Medium -> 10.sp
                TeseraSize.Big -> 12.sp
            }
        ),
        caption = TextStyle(
            fontSize = when (textSize) {
                TeseraSize.Small -> 8.sp
                TeseraSize.Medium -> 10.sp
                TeseraSize.Big -> 12.sp
            }
        )
    )
    CompositionLocalProvider(
        LocalColorProvider provides colors,
        LocalTypographyProvider provides typography,
        LocalPaddingProvider provides paddings,
        LocalComponentSizeProvider provides sizes,
        LocalShapesProvider provides shapes,
        content = content
    )
}