package com.dviss.currencyrates.ui

import androidx.compose.runtime.Stable
import com.dviss.currencyrates.domain.model.Currency
import com.dviss.currencyrates.domain.model.FavouritePair
import com.dviss.currencyrates.domain.model.Filter

@Stable
data class AppState(
    val currencies: List<Currency> = emptyList(),
    val favourites: List<FavouritePair> = emptyList(),
    val filter: Filter = Filter.CODE_A_Z,
    val mainCurrency: String = ""
) {
    val mainCurrencyRate =
        currencies.firstOrNull { it.code == mainCurrency }?.rate ?: 1.0
}
