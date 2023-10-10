package com.dviss.currencyrates.domain.di

import com.dviss.currencyrates.domain.preferences.AppPreferences
import com.dviss.currencyrates.domain.repository.CurrencyRepository
import com.dviss.currencyrates.domain.usecase.AppUseCases
import com.dviss.currencyrates.domain.usecase.GetCurrencies
import com.dviss.currencyrates.domain.usecase.GetUserPreferences
import com.dviss.currencyrates.domain.usecase.SaveFavourites
import com.dviss.currencyrates.domain.usecase.SaveMainCurrency
import com.dviss.currencyrates.domain.usecase.SetFilter
import com.dviss.currencyrates.domain.usecase.UpdateRates
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @ViewModelScoped
    @Provides
    fun provideUseCases(
        repository: CurrencyRepository,
        preferences: AppPreferences
    ): AppUseCases {
        return AppUseCases(
            getCurrencies = GetCurrencies(repository),
            getUserPreferences = GetUserPreferences(preferences),
            saveFavourites = SaveFavourites(preferences),
            setFilter = SetFilter(preferences),
            saveMainCurrency = SaveMainCurrency(preferences),
            updateRates = UpdateRates(repository)
        )
    }
}