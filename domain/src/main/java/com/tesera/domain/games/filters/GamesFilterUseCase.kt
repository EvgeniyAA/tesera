package com.tesera.domain.games.filters

import javax.inject.Inject

class GamesFilterUseCase @Inject constructor(
    private val gamesFilterRepository: GamesFilterRepository,
) {
    fun setFilter(gamesFilter: GamesFilter) = gamesFilterRepository.setFilter(gamesFilter)
    fun getFilter() = gamesFilterRepository.getFilter()
}