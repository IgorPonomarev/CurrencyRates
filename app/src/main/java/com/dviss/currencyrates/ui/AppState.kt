package com.dviss.currencyrates.ui

import com.dviss.currencyrates.domain.model.Currency
import com.dviss.currencyrates.domain.model.FavouritePair
import com.dviss.currencyrates.domain.model.Filter

data class AppState(
    val currencies: List<Currency> = emptyList(),
    val favourites: List<FavouritePair> = emptyList(),
    val filter: Filter = Filter.CODE_A_Z,
    val mainCurrency: String = ""
)
