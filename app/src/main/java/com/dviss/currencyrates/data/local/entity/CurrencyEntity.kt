package com.dviss.currencyrates.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dviss.currencyrates.domain.model.Currency

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey val code: String,
    val rate: Double
)
fun CurrencyEntity.toCurrency(): Currency {
    return Currency(
        code = code,
        rate = rate
    )
}
fun Currency.toCurrencyEntity(): CurrencyEntity {
    return CurrencyEntity(
        code = code,
        rate = rate
    )
}

