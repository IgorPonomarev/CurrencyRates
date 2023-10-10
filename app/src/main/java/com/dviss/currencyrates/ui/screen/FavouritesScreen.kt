package com.dviss.currencyrates.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dviss.currencyrates.R
import com.dviss.currencyrates.domain.model.Currency
import com.dviss.currencyrates.domain.model.FavouritePair
import com.dviss.currencyrates.ui.AppViewModel

@Composable
fun FavouritesScreen(
    viewModel: AppViewModel = hiltViewModel()
) {
    val state by viewModel.appState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Divider(
            color = MaterialTheme.colorScheme.outline
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            if (state.favourites.isNotEmpty()) {
                items(state.favourites) {
                    FavouritePairListItem(
                        favouritePair = it,
                        currencies = state.currencies,
                    ) {
                        viewModel.removeFavouritePair(it)
                    }
                }
            } else {
                item {
                    Text(
                        text = stringResource(id = R.string.nothing_here_yet),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Divider(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun FavouritePairListItem(
    favouritePair: FavouritePair,
    currencies: List<Currency>,
    modifier: Modifier = Modifier,
    onRemoveFromFavouritesClick: () -> Unit
) {
    val rate1 by remember {
        mutableStateOf(
            currencies.firstOrNull { it.code == favouritePair.code1 }?.rate ?: 1.0
        )
    }
    val rate2 by remember {
        mutableStateOf(
            currencies.firstOrNull { it.code == favouritePair.code2 }?.rate ?: 1.0
        )
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = favouritePair.code1 + "/" + favouritePair.code2)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "%.6f".format(rate2 / rate1), fontWeight = FontWeight.SemiBold)

        IconButton(onClick = { onRemoveFromFavouritesClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_favourite),
                contentDescription = stringResource(id = R.string.icon_description_remove_from_favourites),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}