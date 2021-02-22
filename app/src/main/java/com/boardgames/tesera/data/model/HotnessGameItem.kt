package com.boardgames.tesera.data.model

sealed class BoardgameItem(val id: String) {

}

data class HotnessGameItem(
    val title: String
) : BoardgameItem(title)

data class HeaderHotnessGame(
    val title: String
) : BoardgameItem("0")