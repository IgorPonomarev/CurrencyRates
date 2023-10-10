package com.dviss.currencyrates.domain.usecase

import com.dviss.currencyrates.domain.model.Currency
import com.dviss.currencyrates.domain.model.Filter

class FilterCurrencies {
    operator fun invoke(currencies: List<Currency>, filter: Filter): List<Currency> {
        return when (filter) {
            Filter.CODE_A_Z -> {
                currencies.sortedBy { it.code }
            }
            Filter.CODE_Z_A -> {
                currencies.sortedByDescending { it.code }
            }
            Filter.QUOTE_ASC -> {
                currencies.sortedBy { it.rate }
            }
            Filter.QUOTE_DESC -> {
                currencies.sortedByDescending { it.rate }
            }
        }
    }
}