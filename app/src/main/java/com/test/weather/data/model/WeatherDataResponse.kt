package com.test.weather.data.model

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class WeatherDataResponse(

    @param:Json(name = "current")
    val currentWeather: CurrentWeather? = null,

    @param:Json(name = "timezone")
    val timezone: String? = null,

    @param:Json(name = "timezone_offset")
    val timezoneOffset: Int? = null,

    @param:Json(name = "lon")
    val lon: Double? = null,

    @param:Json(name = "hourly")
    val hourly: List<HourlyItem>? = null,

    @param:Json(name="lat")
    val lat: Double? = null
)

@JsonClass(generateAdapter = true)
data class WeatherItem(

    @param:Json(name="icon")
    val icon: String? = null,

    @param:Json(name="description")
    val description: String? = null,

    @param:Json(name="main")
    val main: String? = null,

    @param:Json(name="id")
    val id: Int? = null
)

@JsonClass(generateAdapter = true)
data class Rain(

    @param:Json(name="1h")
    val jsonMember1h: Double? = null
)

@JsonClass(generateAdapter = true)
data class CurrentWeather(

    @param:Json(name="sunrise")
    val sunrise: Int? = null,

    @param:Json(name="temp")
    val temp: Double? = null,

    @param:Json(name="visibility")
    val visibility: Int? = null,

    @param:Json(name="uvi")
    val uvi: Double? = null,

    @param:Json(name="pressure")
    val pressure: Int? = null,

    @param:Json(name="clouds")
    val clouds: Int? = null,

    @param:Json(name="feels_like")
    val feelsLike: Double? = null,

    @param:Json(name="wind_gust")
    val windGust: Double? = null,

    @param:Json(name="dt")
    val dt: Long? = null,

    @param:Json(name="wind_deg")
    val windDeg: Int? = null,

    @param:Json(name="dew_point")
    val dewPoint: Double? = null,

    @param:Json(name="sunset")
    val sunset: Int? = null,

    @param:Json(name="weather")
    val weather: List<WeatherItem?>? = null,

    @param:Json(name="humidity")
    val humidity: Int? = null,

    @param:Json(name="wind_speed")
    val windSpeed: Double? = null
)

@JsonClass(generateAdapter = true)
data class HourlyItem(

    @param:Json(name="temp")
    val temp: Double? = null,

    @param:Json(name="visibility")
    val visibility: Int? = null,

    @param:Json(name="uvi")
    val uvi: Double? = null,

    @param:Json(name="pressure")
    val pressure: Int? = null,

    @param:Json(name="clouds")
    val clouds: Int? = null,

    @param:Json(name="feels_like")
    val feelsLike: Double? = null,

    @param:Json(name="wind_gust")
    val windGust: Double? = null,

    @param:Json(name="dt")
    val dt: Long? = null,

    @param:Json(name="pop")
    val pop: Double? = null,

    @param:Json(name="wind_deg")
    val windDeg: Int? = null,

    @param:Json(name="dew_point")
    val dewPoint: Double? = null,

    @param:Json(name="weather")
    val weather: List<WeatherItem?>? = null,

    @param:Json(name="humidity")
    val humidity: Int? = null,

    @param:Json(name="wind_speed")
    val windSpeed: Double? = null,

    @param:Json(name="rain")
    val rain: Rain? = null
)
