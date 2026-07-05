package com.test.weather.presentation.weather

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.weather.R
import com.test.weather.presentation.common.BaseScreen
import com.test.weather.presentation.common.component.TwoSectorResponsiveLayout
import com.test.weather.presentation.weather.component.ResultSector
import com.test.weather.presentation.weather.component.SearchSector
import com.test.weather.presentation.util.DeviceConfiguration

@Composable
fun WeatherScreen(modifier: Modifier = Modifier, viewModel: WeatherViewModel = hiltViewModel()) {
    val uiState = viewModel.state.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }
    val dismissLabel = stringResource(R.string.dismiss)

    //responsive
    val windowInfo = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowInfo)
    val isWideScreen = when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> true
        else -> false
    }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            try {
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = dismissLabel,
                    duration = SnackbarDuration.Short
                )
            } finally {
                viewModel.onSnackbarShown()
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
                    onSearchTextChange = viewModel::onSearchTextChange,
                    searchText = uiState.searchText,
                    onSearchClick = viewModel::getCityData,
                    onSelectCity = viewModel::onSelectCity,
                    citySearchState = uiState.citySearchState
                )
            },
            endContent = { innerModifier ->
                ResultSector(
                    modifier = innerModifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(16.dp),
                    weatherState = uiState.weatherState
                )
            }
        )
    }
}
