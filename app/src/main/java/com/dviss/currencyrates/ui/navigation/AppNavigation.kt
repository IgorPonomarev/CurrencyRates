package com.dviss.currencyrates.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dviss.currencyrates.ui.AppViewModel
import com.dviss.currencyrates.ui.components.MyBottomBar
import com.dviss.currencyrates.ui.components.MyTopAppBar
import com.dviss.currencyrates.ui.screen.CurrenciesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    //state of current navigation route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Route.CURRENCIES

    val viewModel: AppViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(viewModel = viewModel, navController = navController)
        },
        bottomBar = {
            MyBottomBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.CURRENCIES,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.CURRENCIES) {
                CurrenciesScreen(viewModel)
            }
            composable(Route.FAVOURITES) {

            }
            composable(Route.FILTERS) {

            }
        }
    }
}