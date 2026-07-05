package com.test.weather.domain.usecase

import com.test.weather.data.repository.WeatherRepository
import jakarta.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(query: String, limit: Int) = repository.searchCities(query, limit)
}
