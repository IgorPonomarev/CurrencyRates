package com.dviss.currencyrates.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dviss.currencyrates.R
import com.dviss.currencyrates.domain.model.Currency
import com.dviss.currencyrates.domain.model.FavouritePair
import com.dviss.currencyrates.ui.AppViewModel
import com.dviss.currencyrates.ui.navigation.Route

@Composable
fun CurrenciesScreen(
    navController: NavController,
    viewModel: AppViewModel = hiltViewModel()
) {
    val state by viewModel.appState.collectAsState()

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    val interactionSource = remember { MutableInteractionSource() }

    val filteredCurrencies by remember(state.currencies, state.filter) {
        mutableStateOf(viewModel.filteredCurrencies())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondary,
                            RoundedCornerShape(8.dp)
                        )
                        .background(MaterialTheme.colorScheme.background)
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = state.mainCurrency)
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "dropdown menu",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    modifier = Modifier
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondary,
                            RoundedCornerShape(8.dp)
                        )
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    IconButton(onClick = {
                        navController.navigate(Route.FILTERS)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_filter),
                            contentDescription = "filter",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Divider(color = MaterialTheme.colorScheme.outline)
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 8.dp)
            ) {
                items(filteredCurrencies) { currency ->
                    CurrencyListItem(
                        currency = currency,
                        isFavourite = FavouritePair(
                            state.mainCurrency,
                            currency.code
                        ) in state.favourites,
                        modifier = Modifier.padding(top = 8.dp),
                        onAddToFavouritesClick = {
                            viewModel.addFavouritePair(
                                FavouritePair(
                                    state.mainCurrency,
                                    currency.code
                                )
                            )
                        },
                        onRemoveFromFavouritesClick = {
                            viewModel.removeFavouritePair(
                                FavouritePair(
                                    state.mainCurrency,
                                    currency.code
                                )
                            )
                        }
                    )
                }
            }
        }
        Divider(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.outline
        )
        if (expanded) {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    expanded = false
                }) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.background,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .padding(end = 56.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondary,
                            RoundedCornerShape(8.dp)
                        )
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .padding(start = 16.dp, bottom = 8.dp, top = 8.dp)
                                .clickable {
                                    expanded = false
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = state.mainCurrency)
                            IconButton(onClick = { expanded = false }) {
                                Icon(
                                    Icons.Default.KeyboardArrowUp,
                                    contentDescription = "close dropdown menu",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Divider()
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            items(state.currencies) {
                                val selectedModifier =
                                    if (it.code == state.mainCurrency)
                                        Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                                    else
                                        Modifier
                                Row(
                                    modifier = selectedModifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .clickable {
                                            viewModel.updateMainCurrency(it.code)
                                            expanded = false
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = it.code)
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun CurrencyListItem(
    currency: Currency,
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
    onAddToFavouritesClick: () -> Unit,
    onRemoveFromFavouritesClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = currency.code)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = currency.rate.toString(), fontWeight = FontWeight.SemiBold)
        if (isFavourite) {
            IconButton(onClick = { onRemoveFromFavouritesClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_favourite),
                    contentDescription = "add to favourite",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        } else {
            IconButton(onClick = { onAddToFavouritesClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_favourite_outline),
                    contentDescription = "add to favourite",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}