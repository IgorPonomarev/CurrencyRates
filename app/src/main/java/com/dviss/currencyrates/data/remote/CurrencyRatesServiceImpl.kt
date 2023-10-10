package com.dviss.currencyrates.data.remote

import android.util.Log
import com.dviss.currencyrates.data.remote.dto.LatestResponse
import com.dviss.currencyrates.domain.model.Currency
import com.dviss.currencyrates.domain.service.CurrencyRatesService
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers

private const val TAG = "CurrencyRatesServiceImp"

class CurrencyRatesServiceImpl(
    private val client: HttpClient,
    private val apiKey: String
): CurrencyRatesService {
    override suspend fun getCurrencyRates(): List<Currency> {
        val result = try {
            client.get(HttpRoutes.MAIN_URL + ApiActions.Latest.NAME) {
                headers {
                    append(ApiActions.Latest.Headers.apiKey, apiKey)
                }
            }.body<LatestResponse>()
        } catch (e: Exception) {
            parseException(e, ApiActions.Latest.NAME)
            return emptyList()
        }
        return result.rates.map { Currency(it.key, it.value) }
    }

    private fun parseException(e: Exception, actionName: String) {
        when (e) {
            is RedirectResponseException -> Log.d(TAG, "$actionName: ${e.response.status.description}")
            is ClientRequestException -> Log.d(TAG, "$actionName: ${e.response.status.description}")
            is ServerResponseException -> Log.d(TAG, "$actionName: ${e.response.status.description}")
            is NoTransformationFoundException -> Log.d(TAG, "$actionName: ${e.message}")
            else -> Log.d(TAG, "$actionName: ${e.message}")
        }
    }
}