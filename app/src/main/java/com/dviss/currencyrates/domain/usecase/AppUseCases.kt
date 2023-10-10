package com.dviss.currencyrates.domain.usecase

data class AppUseCases (
    val getCurrencies: GetCurrencies,
    val getUserPreferences: GetUserPreferences,
    val saveFavourites: SaveFavourites,
    val setFilter: SetFilter,
    val saveMainCurrency: SaveMainCurrency,
    val updateRates: UpdateRates,
    val filterCurrencies: FilterCurrencies
)
