package com.dviss.currencyrates.domain.usecase

import com.dviss.currencyrates.domain.model.FavouritePair
import com.dviss.currencyrates.domain.preferences.AppPreferences

class SaveFavourites(
    private val preferences: AppPreferences
) {
    suspend operator fun invoke(favourites: List<FavouritePair>) {
        preferences.saveFavourites(favourites)
    }
}