package com.tesera.designsystem.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

data class TeseraShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val smallTop: RoundedCornerShape,
    val mediumTop: RoundedCornerShape,
    val circle: RoundedCornerShape
)

val shapes = TeseraShapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(8.dp),
    smallTop = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
    mediumTop = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    circle = CircleShape
)