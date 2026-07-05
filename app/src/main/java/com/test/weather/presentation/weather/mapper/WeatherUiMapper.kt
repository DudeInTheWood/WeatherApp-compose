package com.test.weather.presentation.weather.mapper

import com.test.weather.domain.model.City
import com.test.weather.domain.model.HourlyWeather
import com.test.weather.domain.model.Weather
import com.test.weather.presentation.util.toTime
import com.test.weather.presentation.weather.model.CityUiModel
import com.test.weather.presentation.weather.model.HourlyWeatherUiModel
import com.test.weather.presentation.weather.model.WeatherUiModel
import kotlin.math.roundToInt

fun City.toUiModel(): CityUiModel {
    return CityUiModel(
        name = name,
        country = country,
        state = state,
        lat = lat,
        lon = lon
    )
}

fun Weather.toUiModel(city: CityUiModel): WeatherUiModel {
    return WeatherUiModel(
        cityName = city.displayName,
        currentTemperature = formatTemperature(currentTemperature),
        feelsLikeTemperature = formatTemperature(feelsLikeTemperature),
        description = description,
        hourlyForecast = hourlyForecast.map { it.toUiModel(timezone) }
    )
}

private fun HourlyWeather.toUiModel(timezone: String?): HourlyWeatherUiModel {
    return HourlyWeatherUiModel(
        temperature = formatTemperatureValue(temperature),
        time = epochSecond?.toTime(timezone).orEmpty(),
        iconUrl = iconCode?.let { icon ->
            "https://openweathermap.org/img/wn/$icon@2x.png"
        }.orEmpty()
    )
}

private fun formatTemperature(temp: Double?): String {
    return "${formatTemperatureValue(temp)}°"
}

private fun formatTemperatureValue(temp: Double?): String {
    return temp?.roundToInt()?.toString() ?: "--"
}
