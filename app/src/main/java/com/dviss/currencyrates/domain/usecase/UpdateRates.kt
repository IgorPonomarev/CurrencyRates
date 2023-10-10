package com.dviss.currencyrates.domain.usecase

import com.dviss.currencyrates.domain.repository.CurrencyRepository

class UpdateRates(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke() {
        repository.updateCurrencyRates()
    }
}