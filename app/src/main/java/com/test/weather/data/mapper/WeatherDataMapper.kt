package com.test.weather.data.mapper

import com.test.weather.data.remote.dto.CityDataResponse
import com.test.weather.data.remote.dto.HourlyItem
import com.test.weather.data.remote.dto.WeatherDataResponse
import com.test.weather.domain.model.City
import com.test.weather.domain.model.HourlyWeather
import com.test.weather.domain.model.Weather

fun CityDataResponse.toDomain(): City? {
    val lat = lat ?: return null
    val lon = lon ?: return null

    return City(
        name = name.orEmpty(),
        country = country.orEmpty(),
        state = state,
        lat = lat,
        lon = lon
    )
}

fun WeatherDataResponse.toDomain(): Weather {
    return Weather(
        timezone = timezone,
        currentTemperature = currentWeather?.temp,
        feelsLikeTemperature = currentWeather?.feelsLike,
        description = currentWeather?.weather?.firstOrNull()?.description.orEmpty(),
        hourlyForecast = hourly.orEmpty().map { it.toDomain() }
    )
}

private fun HourlyItem.toDomain(): HourlyWeather {
    return HourlyWeather(
        temperature = temp,
        epochSecond = dt,
        iconCode = weather?.firstOrNull()?.icon
    )
}
