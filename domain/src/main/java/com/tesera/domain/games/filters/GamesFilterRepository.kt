package com.tesera.domain.games.filters

interface GamesFilterRepository {
    fun setFilter(gamesFilter: GamesFilter)
    fun getFilter(): GamesFilter
}
