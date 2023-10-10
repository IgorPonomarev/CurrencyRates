package com.dviss.currencyrates.data.remote

object HttpRoutes {
    const val MAIN_URL = "https://api.apilayer.com/exchangerates_data/"
}

object ApiActions {
    object Latest {
        const val NAME = "latest"

        object Headers {
            const val apiKey = "apikey"
        }
    }
}