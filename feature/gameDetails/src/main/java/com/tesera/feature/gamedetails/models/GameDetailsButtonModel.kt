package com.tesera.feature.gamedetails.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class GameDetailsButtonModel(
    val title: String,
    @DrawableRes val image: Int,
    val count: Int,
    val buttonStartColor: Color,
    val buttonEndColor: Color
)