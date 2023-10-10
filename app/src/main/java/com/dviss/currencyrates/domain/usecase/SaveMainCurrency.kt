package com.dviss.currencyrates.domain.usecase

import com.dviss.currencyrates.domain.preferences.AppPreferences

class SaveMainCurrency(
    private val preferences: AppPreferences
) {
    suspend operator fun invoke(code: String) {
        preferences.saveMainCurrency(code)
    }
}