package com.dviss.currencyrates.domain.usecase

import com.dviss.currencyrates.domain.model.Filter
import com.dviss.currencyrates.domain.preferences.AppPreferences

class SetFilter(
    private val preferences: AppPreferences
) {
    suspend operator fun invoke(filter: Filter) {
        preferences.saveFilter(filter)
    }
}