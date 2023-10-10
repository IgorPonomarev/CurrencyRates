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
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dviss.currencyrates.R
import com.dviss.currencyrates.ui.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    // viewModel: AppViewModel = hiltViewModel(),
    navController: NavController
) {
    //state of current navigation route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Route.CURRENCIES

    val title = when (currentRoute) {
        Route.CURRENCIES -> {
            stringResource(id = R.string.tab_title_currencies)
        }
        Route.FAVOURITES -> {
            stringResource(id = R.string.tab_title_favourites)
        }
        Route.FILTERS -> {
            stringResource(id = R.string.tab_title_filter)
        }
        else -> {""}
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