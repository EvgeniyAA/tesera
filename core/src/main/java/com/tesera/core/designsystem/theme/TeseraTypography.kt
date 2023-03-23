package com.tesera.core.designsystem.theme

import androidx.compose.ui.text.TextStyle

data class TeseraTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val hint: TextStyle
)

enum class TeseraSize {
    Small, Medium, Big
}

enum class TeseraCorners {
    Flat, Rounded
}