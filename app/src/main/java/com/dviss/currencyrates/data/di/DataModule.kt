package com.dviss.currencyrates.data.di

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.dviss.currencyrates.data.local.CurrencyDatabase
import com.dviss.currencyrates.data.preferences.AppPreferencesImpl
import com.dviss.currencyrates.data.remote.CurrencyRatesServiceImpl
import com.dviss.currencyrates.data.repository.CurrencyRepositoryImpl
import com.dviss.currencyrates.domain.preferences.AppPreferences
import com.dviss.currencyrates.domain.repository.CurrencyRepository
import com.dviss.currencyrates.domain.service.CurrencyRatesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideShoppingListDatabase(app: Application): CurrencyDatabase {
        return Room.databaseBuilder(
            app,
            CurrencyDatabase::class.java,
            "ShoppingList_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Provides
    @Singleton
    fun provideShoppingListService(
        client: HttpClient,
        @ApplicationContext appContext: Context
    ): CurrencyRatesService {
//        val applicationInfo = appContext.packageManager.getApplicationInfo(
//            appContext.packageName,
//            PackageManager.GET_META_DATA
//        )
//        val applicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            appContext.packageManager.getApplicationInfo(
//                appContext.packageName,
//                PackageManager.ApplicationInfoFlags.of(0)
//            )
//        } else {
//            appContext.packageManager
//                .getApplicationInfo(appContext.packageName, PackageManager.GET_META_DATA)
//        }

        val applicationInfo: ApplicationInfo = appContext.packageManager
            .getApplicationInfo(appContext.packageName, PackageManager.GET_META_DATA)
        val apiKey = applicationInfo.metaData["API_KEY"].toString()
        //val apiKey = BuildConfig.
        return CurrencyRatesServiceImpl(client, apiKey)
    }

    @Provides
    @Singleton
    fun provideShoppingListRepository(
        db: CurrencyDatabase,
        shoppingListService: CurrencyRatesService
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(db.dao, shoppingListService)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(dataStore: DataStore<Preferences>): AppPreferences {
        return AppPreferencesImpl(dataStore)
    }
}