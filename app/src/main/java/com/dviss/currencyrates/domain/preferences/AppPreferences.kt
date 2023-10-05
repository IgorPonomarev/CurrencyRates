package com.dviss.currencyrates.domain.preferences

import com.dviss.currencyrates.domain.model.FavouritePair
import com.dviss.currencyrates.domain.model.Filter
import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    suspend fun saveFilter(filter: Filter)
    suspend fun saveFavourites(favourites: List<FavouritePair>)
    fun getPreferences(): Flow<UserPreferences>
}