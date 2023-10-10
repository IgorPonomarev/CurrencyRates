package com.dviss.currencyrates.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LatestResponse (
    val success: Boolean,
    val timestamp: String,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)