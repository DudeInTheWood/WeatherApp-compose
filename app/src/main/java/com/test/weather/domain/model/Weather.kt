package com.test.weather.domain.model

data class Weather(
    val timezone: String?,
    val currentTemperature: Double?,
    val feelsLikeTemperature: Double?,
    val description: String,
    val hourlyForecast: List<HourlyWeather>
)

data class HourlyWeather(
    val temperature: Double?,
    val epochSecond: Long?,
    val iconCode: String?
)
