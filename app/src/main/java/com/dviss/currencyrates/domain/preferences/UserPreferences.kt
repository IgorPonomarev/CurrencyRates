package com.dviss.currencyrates.domain.preferences

import com.dviss.currencyrates.domain.model.FavouritePair
import com.dviss.currencyrates.domain.model.Filter

data class UserPreferences(
    val filter: Filter,
    val favourites: List<FavouritePair>
)
