package com.test.weather.domain.repository

import com.test.weather.domain.common.AppResult
import com.test.weather.domain.model.City
import com.test.weather.domain.model.Weather

interface WeatherRepository {
    suspend fun searchCities(query: String, limit: Int): AppResult<List<City>>
    suspend fun getWeather(lat: Double, lon: Double): AppResult<Weather>
}
