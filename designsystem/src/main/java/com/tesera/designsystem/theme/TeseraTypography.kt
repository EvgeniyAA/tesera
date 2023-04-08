package com.tesera.designsystem.theme

import androidx.compose.ui.text.TextStyle

data class TeseraTypography(
    val heading1: TextStyle,
    val heading4: TextStyle,
    val bodyMedium: TextStyle,
    val bodyRegular: TextStyle,
    val hint: TextStyle
)

enum class TeseraSize {
    Small, Medium, Big
}

enum class TeseraCorners {
    Flat, Rounded
}