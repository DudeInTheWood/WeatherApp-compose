package com.test.weather.ui.model.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.weather.ui.model.base.BaseScreen
import com.test.weather.ui.model.weather.component.ResultSector
import com.test.weather.ui.model.weather.component.SearchSector

@Composable
fun WeatherScreen(modifier: Modifier = Modifier, viewModel: WeatherViewModel = hiltViewModel()) {
    val uiState = viewModel.state.collectAsState().value
    val scrollState = rememberScrollState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { message ->
            try {
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = "DISMISS",
                    duration = SnackbarDuration.Short
                )
            } finally {
                viewModel.updateState { copy(error = null) }
            }
        }
    }

    BaseScreen(
        modifier = modifier,
        snackBarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            SearchSector(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                onSearchTextChange = { viewModel.updateState { copy(searchText = it) } },
                searchText = uiState.searchText,
                onSearchClick = viewModel::getCityData,
                onSelectCity = viewModel::onSelectCity,
                cityList = uiState.cityList,
                isLoading = uiState.isCityDataLoading
            )

            Spacer(modifier = Modifier.size(12.dp))

            ResultSector(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 16.dp),
                weatherData = uiState.weatherData,
                selectedGeo = uiState.selectedCity,
                isLoading = uiState.isWeatherDataLoading
            )
        }
    }
}