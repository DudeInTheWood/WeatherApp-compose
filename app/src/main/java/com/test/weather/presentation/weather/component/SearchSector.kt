package com.test.weather.presentation.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.test.weather.R
import com.test.weather.presentation.common.component.LoadingIndicator
import com.test.weather.presentation.theme.BlueLightSky
import com.test.weather.presentation.weather.model.CitySearchState
import com.test.weather.presentation.weather.model.CityUiModel

@Composable
fun SearchSector(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onSelectCity: (CityUiModel) -> Unit,
    citySearchState: CitySearchState
) {
    val onSearchAction = {
        onSearchClick(searchText.trim())
    }
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                placeholder = { Text(stringResource(R.string.enter_city)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchAction() }
                ),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = onSearchAction,
                enabled = searchText.isNotBlank(),
                modifier = Modifier.width(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray
                )
            ) {
                Text(stringResource(R.string.search))
            }

        }

        Box(modifier = Modifier) {
            when (citySearchState) {
                CitySearchState.Idle -> Unit
                CitySearchState.Loading -> {
                    LoadingIndicator(modifier = Modifier)
                }

                CitySearchState.Empty -> {
                    SearchMessage(text = "No city found")
                }

                is CitySearchState.Error -> {
                    SearchMessage(text = citySearchState.message)
                }

                is CitySearchState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 250.dp),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(citySearchState.cities) { cityItem ->
                            CityNameListItem(
                                city = cityItem,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(BlueLightSky)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        onSelectCity(cityItem)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchMessage(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier.padding(12.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = Color.DarkGray
    )
}
