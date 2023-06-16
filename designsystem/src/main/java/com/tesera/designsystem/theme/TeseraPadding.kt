package com.tesera.designsystem.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TeseraPadding(
    val separator: Dp,
    val xxSmall: Dp,
    val xSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val xLarge: Dp,
)

val paddings = TeseraPadding(
    separator = 1.dp,
    xxSmall = 2.dp,
    xSmall = 4.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp,
    xLarge = 32.dp
)