package com.tesera.core.designsystem.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val hintTextColor: Color,
    val primaryTintColor: Color,
    val secondaryTintColor: Color,
    val notificationColor: Color
)

val lightPalette = Colors(
    primaryBackground = Color.White,
    secondaryBackground = Color(0xffd0ccc7),
    primaryTextColor = Color(0xFFFF8A00),
    secondaryTextColor = Color.White,
    primaryTintColor = Color(0xFFFF8A00),
    secondaryTintColor = Color(0xFF3FA72F),
    hintTextColor = Color(0xFFA0978C),
    notificationColor = Color.Red
)

val darkPalette = Colors(
    primaryBackground = Color.DarkGray,
    secondaryBackground = Color.Black,
    primaryTextColor = Color(0xFFFF8A00),
    secondaryTextColor = Color.DarkGray,
    primaryTintColor = Color(0xFFFF8A00),
    secondaryTintColor = Color(0xFF3FA72F),
    hintTextColor = Color(0xFFA0978C),
    notificationColor = Color.Red
)
