package com.dviss.currencyrates.domain.service

import com.dviss.currencyrates.domain.model.Currency

interface CurrencyRatesService {

    suspend fun getCurrencyRates(): List<Currency>
}