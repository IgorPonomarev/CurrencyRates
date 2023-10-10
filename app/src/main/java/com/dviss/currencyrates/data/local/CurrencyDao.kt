package com.dviss.currencyrates.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dviss.currencyrates.data.local.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCurrencyRates(rates: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies")
    fun getCurrencies(): Flow<List<CurrencyEntity>>
}