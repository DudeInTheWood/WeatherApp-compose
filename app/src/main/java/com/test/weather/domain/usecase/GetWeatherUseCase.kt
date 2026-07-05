package com.test.weather.domain.usecase

import com.test.weather.domain.repository.WeatherRepository
import jakarta.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) = repository.getWeather(lat, lon)
}
