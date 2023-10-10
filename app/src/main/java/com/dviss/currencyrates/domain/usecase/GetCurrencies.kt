package com.dviss.currencyrates.domain.usecase

import com.dviss.currencyrates.domain.model.Currency
import com.dviss.currencyrates.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetCurrencies(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): Flow<List<Currency>> {
        return repository.getCurrencyRates()
    }
}