package com.test.weather.ui.model.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.weather.ui.model.base.BaseScreen
import com.test.weather.ui.model.base.component.TwoSectorResponsiveLayout
import com.test.weather.ui.model.weather.component.ResultSector
import com.test.weather.ui.model.weather.component.SearchSector
import com.test.weather.ui.util.DeviceConfiguration

@Composable
fun WeatherScreen(modifier: Modifier = Modifier, viewModel: WeatherViewModel = hiltViewModel()) {
    val uiState = viewModel.state.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    //responsive
    val windowInfo = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowInfo)
    val isWideScreen = when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> true
        else -> false
    }

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
        snackBarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        TwoSectorResponsiveLayout(
            isWideScreen = isWideScreen,
            modifier = Modifier.padding(paddingValues),
            startContent = { innerModifier ->
                SearchSector(
                    modifier = innerModifier
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
            },
            endContent = { innerModifier ->
                ResultSector(
                    modifier = innerModifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(16.dp),
                    weatherData = uiState.weatherData,
                    selectedGeo = uiState.selectedCity,
                    isLoading = uiState.isWeatherDataLoading
                )
            }
        )
    }
}