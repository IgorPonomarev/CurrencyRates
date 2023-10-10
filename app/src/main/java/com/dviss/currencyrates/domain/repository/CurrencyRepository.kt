package com.dviss.currencyrates.domain.repository

import com.dviss.currencyrates.domain.model.Currency
import com.dviss.currencyrates.domain.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getCurrencyRates(): Flow<List<Currency>>
    suspend fun updateCurrencyRates(): NetworkResponse

}