package com.dviss.currencyrates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dviss.currencyrates.ui.navigation.AppNavigation
import com.dviss.currencyrates.ui.theme.CurrencyRatesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyRatesTheme {
                AppNavigation()
            }
        }
    }
}