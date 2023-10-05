package com.dviss.currencyrates.domain.model

sealed class NetworkResponse {
    object Success: NetworkResponse()
    data class Error(val message: String): NetworkResponse()
}
