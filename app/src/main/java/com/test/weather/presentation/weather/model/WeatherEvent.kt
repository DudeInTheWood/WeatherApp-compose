package com.test.weather.presentation.weather.model

sealed interface WeatherEvent {
    data class ShowSnackbar(val message: String) : WeatherEvent
}
