package com.tesera.designsystem.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val hintTextColor: Color,
    val primaryTintColor: Color,
    val secondaryTintColor: Color,
    val notificationColor: Color,
    val interactiveBackground: Color,
    val increaseTextColor: Color
)

private val orange = Color(0xFFFF8A00)

val lightPalette = Colors(
    primaryBackground = Color.White,
    secondaryBackground = orange,
    primaryTextColor = orange,
    secondaryTextColor = Color.Black,
    primaryTintColor = orange,
    secondaryTintColor = Color.White,
    hintTextColor = Color.White,
    notificationColor = Color.Red,
    interactiveBackground = Color(0xFFE7E7E7),
    increaseTextColor = Color(0xFF008000)
)

val darkPalette = Colors(
    primaryBackground = Color.DarkGray,
    secondaryBackground = Color.Black,
    primaryTextColor = orange,
    secondaryTextColor = Color.Black,
    primaryTintColor = orange,
    secondaryTintColor = Color(0xFF3FA72F),
    hintTextColor = Color.White,
    notificationColor = Color.Red,
    interactiveBackground = Color(0xFFFFFFFF),
    increaseTextColor = Color(0xFF008000)
)
