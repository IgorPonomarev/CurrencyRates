package com.dviss.currencyrates.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dviss.currencyrates.R
import com.dviss.currencyrates.ui.AppViewModel
import com.dviss.currencyrates.ui.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    viewModel: AppViewModel = hiltViewModel(),
    navController: NavController
) {
    //state of current navigation route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Route.CURRENCIES

    // state
    val state by viewModel.appState.collectAsState()

    val title =
        if (currentRoute == Route.CURRENCIES) {
            "Currencies"
        } else if (currentRoute == Route.FAVOURITES) {
            "Favourites"
        } else {
            "Filters"
        }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        navigationIcon = {
            if (currentRoute == Route.FILTERS) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, "back")
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )


}