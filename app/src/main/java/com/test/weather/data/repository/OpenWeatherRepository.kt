package com.test.weather.data.repository

import com.test.weather.BuildConfig
import com.test.weather.data.constant.ALERTS
import com.test.weather.data.constant.DAILY
import com.test.weather.data.constant.MINUTELY
import com.test.weather.data.constant.UNITS_METRIC
import com.test.weather.data.mapper.toDomain
import com.test.weather.data.network.NetworkHandler
import com.test.weather.data.network.Resource
import com.test.weather.data.remote.api.WeatherApi
import com.test.weather.domain.common.AppResult
import com.test.weather.domain.model.City
import com.test.weather.domain.model.Weather
import com.test.weather.domain.repository.WeatherRepository
import jakarta.inject.Inject

class OpenWeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val networkHandler: NetworkHandler
) : WeatherRepository {
    override suspend fun searchCities(query: String, limit: Int): AppResult<List<City>> {
        return when (val result = networkHandler.handleNetworkCall {
            api.getCityData(
                city = query,
                limit = limit,
                apiKey = BuildConfig.API_KEY
            ).orEmpty().filterNotNull()
        }) {
            is Resource.Success -> AppResult.Success(
                result.data.orEmpty().mapNotNull { it.toDomain() }
            )

            is Resource.Error -> AppResult.Error(result.message.orEmpty())
        }
    }

    override suspend fun getWeather(
        lat: Double,
        lon: Double
    ): AppResult<Weather> {
        val exclude = "${MINUTELY},${DAILY},${ALERTS}"

        return when (val result = networkHandler.handleNetworkCall {
            api.getWeatherData(
                lat = lat,
                lon = lon,
                exclude = exclude,
                units = UNITS_METRIC,
                apiKey = BuildConfig.API_KEY
            )
        }) {
            is Resource.Success -> result.data?.let { weather ->
                AppResult.Success(weather.toDomain())
            } ?: AppResult.Error("Weather data is unavailable.")

            is Resource.Error -> AppResult.Error(result.message.orEmpty())
        }
    }
}
