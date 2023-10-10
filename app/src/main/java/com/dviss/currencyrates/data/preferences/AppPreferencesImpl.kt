package com.dviss.currencyrates.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dviss.currencyrates.domain.model.FavouritePair
import com.dviss.currencyrates.domain.model.Filter
import com.dviss.currencyrates.domain.preferences.AppPreferences
import com.dviss.currencyrates.domain.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : AppPreferences {

    private object PreferencesKeys {
        val FILTER = stringPreferencesKey("filter")
        val FAVOURITES = stringPreferencesKey("favourites")
        val MAIN_CURRENCY = stringPreferencesKey("main_currency")
        const val FAVOURITES_SEPARATOR = ","
        const val FAVOURITE_PAIR_SEPARATOR = "/"
    }

    override suspend fun saveFilter(filter: Filter) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FILTER] = filter.name
        }
    }

    override suspend fun saveMainCurrency(code: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.MAIN_CURRENCY] = code
        }
    }

    override suspend fun saveFavourites(favourites: List<FavouritePair>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FAVOURITES] =
                favourites
                    .map { it.code1 + PreferencesKeys.FAVOURITE_PAIR_SEPARATOR + it.code2 }
                    .joinToString(PreferencesKeys.FAVOURITES_SEPARATOR)
        }
    }

    override fun getPreferences(): Flow<UserPreferences> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val prefFavourites = preferences[PreferencesKeys.FAVOURITES]
        val favourites = if (prefFavourites == null || prefFavourites == "") {
            emptyList()
        } else {
            prefFavourites.split(PreferencesKeys.FAVOURITES_SEPARATOR).map {
                val codes = it.split(PreferencesKeys.FAVOURITE_PAIR_SEPARATOR)
                FavouritePair(codes[0], codes[1])
            }
        }

        return UserPreferences(
            filter = Filter.valueOf(preferences[PreferencesKeys.FILTER] ?: Filter.CODE_A_Z.name),
            mainCurrency = preferences[PreferencesKeys.MAIN_CURRENCY] ?: "",
            favourites = favourites
        )
    }
}