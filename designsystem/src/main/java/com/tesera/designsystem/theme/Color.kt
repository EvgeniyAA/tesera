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
    val darkTintColor: Color,
    val notificationColor: Color,
    val interactiveBackground: Color,
    val increaseTextColor: Color,
    val bggBackgroundGradientStart: Color,
    val bggBackgroundGradientEnd : Color,
    val teseraBackgroundGradientStart: Color,
    val teseraBackgroundGradientEnd: Color,
    val lightTextColor: Color
)

private val orange = Color(0xFFFF8A00)

val lightPalette = Colors(
    primaryBackground = Color.White,
    secondaryBackground = orange,
    primaryTextColor = orange,
    secondaryTextColor = Color.Black,
    primaryTintColor = orange,
    secondaryTintColor = Color.White,
    darkTintColor = Color.Black,
    hintTextColor = Color.White,
    notificationColor = Color.Red,
    interactiveBackground = Color(0xFFE7E7E7),
    increaseTextColor = Color(0xFF008000),
    bggBackgroundGradientStart = Color(0xFF2f3f79),
    bggBackgroundGradientEnd = Color(0xFF1b2547),
    teseraBackgroundGradientStart = Color(0xFFf99c00),
    teseraBackgroundGradientEnd = Color(0xFFf55c00),
    lightTextColor = Color.White
)

val darkPalette = Colors(
    primaryBackground = Color.DarkGray,
    secondaryBackground = Color.Black,
    primaryTextColor = orange,
    secondaryTextColor = Color.Black,
    primaryTintColor = orange,
    secondaryTintColor = Color(0xFF3FA72F),
    darkTintColor = Color.Black,
    hintTextColor = Color.White,
    notificationColor = Color.Red,
    interactiveBackground = Color(0xFFFFFFFF),
    increaseTextColor = Color(0xFF008000),
    bggBackgroundGradientStart = Color(0xFF2f3f79),
    bggBackgroundGradientEnd = Color(0xFF1b2547),
    teseraBackgroundGradientStart = Color(0xFFf99c00),
    teseraBackgroundGradientEnd = Color(0xFFf55c00),
    lightTextColor = Color.White
)
