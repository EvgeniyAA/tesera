package com.tesera.data.repository.local

import com.tesera.domain.games.filters.GamesFilter
import com.tesera.domain.games.filters.GamesFilterRepository
import com.tesera.domain.games.filters.GamesType
import javax.inject.Inject

class LocalGamesFilterRepository @Inject constructor() : GamesFilterRepository {
    private var gamesFilter = GamesFilter(type = GamesType.HOTNESS)
    override fun setFilter(gamesFilter: GamesFilter) {
        this.gamesFilter = gamesFilter
    }

    override fun getFilter(): GamesFilter {
        return gamesFilter
    }
}