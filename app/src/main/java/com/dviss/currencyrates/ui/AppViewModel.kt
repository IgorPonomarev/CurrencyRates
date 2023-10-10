package com.dviss.currencyrates.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dviss.currencyrates.domain.model.FavouritePair
import com.dviss.currencyrates.domain.model.Filter
import com.dviss.currencyrates.domain.usecase.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val useCases: AppUseCases
) : ViewModel() {

    private val _appState = MutableStateFlow(AppState())
    val appState = _appState.asStateFlow()

    init {
        viewModelScope.launch {
            useCases.updateRates()
        }
        viewModelScope.launch {
            useCases.getCurrencies().collect { newCurrencies ->
                _appState.update { it.copy(currencies = newCurrencies) }
            }
        }
        viewModelScope.launch {
            useCases.getUserPreferences().collect { newPreferences ->
                _appState.update {
                    it.copy(
                        filter = newPreferences.filter,
                        favourites = newPreferences.favourites,
                        mainCurrency = if (newPreferences.mainCurrency == "") "USD" else newPreferences.mainCurrency
                    )
                }
            }
        }
    }

    fun addFavouritePair(pair: FavouritePair) {
        val newPairs = appState.value.favourites + pair
        viewModelScope.launch {
            useCases.saveFavourites(newPairs)
        }
    }

    fun removeFavouritePair(pair: FavouritePair) {
        val newPairs = appState.value.favourites.toMutableList()
        newPairs.remove(pair)
        viewModelScope.launch {
            useCases.saveFavourites(newPairs)
        }
    }

    fun setFilter(filter: Filter) {
        viewModelScope.launch {
            useCases.setFilter(filter)
        }
    }

    fun updateMainCurrency(code: String) {
        viewModelScope.launch {
            useCases.saveMainCurrency(code)
        }
    }
}