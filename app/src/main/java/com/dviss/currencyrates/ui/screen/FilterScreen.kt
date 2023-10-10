package com.dviss.currencyrates.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dviss.currencyrates.R
import com.dviss.currencyrates.domain.model.Filter
import com.dviss.currencyrates.ui.AppViewModel

@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: AppViewModel = hiltViewModel()
) {
    val state by viewModel.appState.collectAsState()
    var selectedFiler by rememberSaveable {
        mutableStateOf(state.filter)
    }

    Divider(color = MaterialTheme.colorScheme.outline)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.sort_by),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Filter.values().forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedFiler = it
                    },
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = getFilterName(filter = it))
                    RadioButton(
                        selected = it == selectedFiler,
                        onClick = { selectedFiler = it },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = MaterialTheme.colorScheme.secondary
                        )

                    )
                }
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                if (state.filter != selectedFiler) viewModel.setFilter(selectedFiler)
                navController.navigateUp()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.apply))
        }
    }
}

@Composable
fun getFilterName(filter: Filter): String {
    return when (filter) {
        Filter.CODE_A_Z -> stringResource(id = R.string.filter_code_a_z)
        Filter.CODE_Z_A -> stringResource(id = R.string.filter_code_z_a)
        Filter.QUOTE_ASC -> stringResource(id = R.string.filter_quote_asc)
        Filter.QUOTE_DESC -> stringResource(id = R.string.filter_quote_desc)
    }
}