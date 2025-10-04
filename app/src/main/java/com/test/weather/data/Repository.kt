package com.test.weather.data

import com.test.weather.BuildConfig
import com.test.weather.data.api.WeatherApi
import com.test.weather.data.common.NetworkHandler
import com.test.weather.data.common.Resource
import com.test.weather.data.model.CityDataResponse
import com.test.weather.data.model.WeatherDataResponse
import jakarta.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val networkHandler: NetworkHandler
) {
    suspend fun fetchCityData(city: String, limit: Int): Resource<List<CityDataResponse?>?> {
        return networkHandler.handleNetworkCall {
            api.getCityData(
                city = city,
                limit = limit,
                apiKey = BuildConfig.API_KEY
            )
        }
    }

    suspend fun fetchWeatherData(
        lat: Double,
        lon: Double,
        exclude: String,
        units: String
    ): Resource<WeatherDataResponse> {
        return networkHandler.handleNetworkCall {
            api.getWeatherData(
                lat = lat,
                lon = lon,
                exclude = exclude,
                units = units,
                apiKey = BuildConfig.API_KEY
            )
        }
    }
}