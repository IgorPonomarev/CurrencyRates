package com.dviss.currencyrates.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dviss.currencyrates.data.local.entity.CurrencyEntity

@Database(
    entities = [
        CurrencyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract val dao: CurrencyDao
}