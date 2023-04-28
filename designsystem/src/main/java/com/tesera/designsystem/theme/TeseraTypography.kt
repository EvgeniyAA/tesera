package com.tesera.designsystem.theme

import androidx.compose.ui.text.TextStyle

data class TeseraTypography(
    val heading1: TextStyle,
    val heading2: TextStyle,
    val heading3: TextStyle,
    val heading4: TextStyle,
    val heading5: TextStyle,
    val heading6: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val bodySmall: TextStyle,
    val hint: TextStyle,
    val caption: TextStyle,
)

enum class TeseraSize {
    Small, Medium, Big
}

enum class TeseraCorners {
    Flat, Rounded
}