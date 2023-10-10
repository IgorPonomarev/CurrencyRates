package com.dviss.currencyrates.domain.usecase

import com.dviss.currencyrates.domain.preferences.AppPreferences
import com.dviss.currencyrates.domain.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow

class GetUserPreferences(
    private val preferences: AppPreferences
) {
    operator fun invoke(): Flow<UserPreferences> {
        return preferences.getPreferences()
    }
}