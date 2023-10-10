package com.dviss.currencyrates.data.repository

import com.dviss.currencyrates.data.local.CurrencyDao
import com.dviss.currencyrates.data.local.entity.toCurrency
import com.dviss.currencyrates.data.local.entity.toCurrencyEntity
import com.dviss.currencyrates.domain.model.Currency
import com.dviss.currencyrates.domain.model.NetworkResponse
import com.dviss.currencyrates.domain.repository.CurrencyRepository
import com.dviss.currencyrates.domain.service.CurrencyRatesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyRepositoryImpl(
    private val dao: CurrencyDao,
    private val service: CurrencyRatesService
) : CurrencyRepository {
    override fun getCurrencyRates(): Flow<List<Currency>> {
        return dao.getCurrencies().map { rates ->
            rates.map { it.toCurrency() }
        }
    }

    override suspend fun updateCurrencyRates(): NetworkResponse {
        val newRates = service.getCurrencyRates()
        return if (newRates.isNotEmpty()) {
            dao.updateCurrencyRates(newRates.map { it.toCurrencyEntity() })
            NetworkResponse.Success
        } else {
            NetworkResponse.Error("network error")
        }
    }
}