package com.dviss.currencyrates.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dviss.currencyrates.R
import com.dviss.currencyrates.ui.navigation.Route

@Composable
fun MyBottomBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Route.CURRENCIES
    if (currentRoute == Route.CURRENCIES || currentRoute == Route.FAVOURITES) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Route.bottomBarRoutes.forEachIndexed { index, route ->
                NavigationBarItem(
                    icon = { GetNavigationBarIcon(route = route) },
                    label = { Text(text = getNavigationBarLabel(route = route)) },
                    selected = currentRoute == route,
                    onClick = {
                        navController.navigate(route) {
                            popUpTo(route) {
                                inclusive = true
                            }

                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.onBackground,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.secondary,
                        unselectedTextColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}

@Composable
fun GetNavigationBarIcon(route: String) {
    when (route) {
        Route.CURRENCIES -> {
            Icon(
                painter = painterResource(id = R.drawable.icon_wallet),
                contentDescription = null
            )
        }
        Route.FAVOURITES -> {
            Icon(
                painter = painterResource(id = R.drawable.icon_favourite),
                contentDescription = null
            )
        }
    }
}

@Composable
fun getNavigationBarLabel(route: String): String {
    return when (route) {
        Route.CURRENCIES -> stringResource(id = R.string.nav_bar_currencies)
        Route.FAVOURITES -> stringResource(id = R.string.nav_bar_favourites)
        else -> ""
    }
}