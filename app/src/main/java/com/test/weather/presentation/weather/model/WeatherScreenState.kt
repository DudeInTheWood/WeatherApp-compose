package com.test.weather.presentation.weather.model

data class WeatherScreenState(
    val searchText: String = "",
    val citySearchState: CitySearchState = CitySearchState.Idle,
    val selectedCity: CityUiModel? = null,
    val weatherState: WeatherContentState = WeatherContentState.Empty
)

sealed interface CitySearchState {
    data object Idle : CitySearchState
    data object Loading : CitySearchState
    data object Empty : CitySearchState
    data class Success(val cities: List<CityUiModel>) : CitySearchState
    data class Error(val message: String) : CitySearchState
}

sealed interface WeatherContentState {
    data object Empty : WeatherContentState
    data object Loading : WeatherContentState
    data class Success(val data: WeatherUiModel) : WeatherContentState
    data class Error(val message: String) : WeatherContentState
}
