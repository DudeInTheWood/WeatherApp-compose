package com.test.weather.presentation.weather.model

data class WeatherUiModel(
    val cityName: String,
    val currentTemperature: String,
    val feelsLikeTemperature: String,
    val description: String,
    val hourlyForecast: List<HourlyWeatherUiModel>
)

data class HourlyWeatherUiModel(
    val temperature: String,
    val time: String,
    val iconUrl: String
)
