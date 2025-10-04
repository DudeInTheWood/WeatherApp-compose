package com.test.weather.data.api

import com.test.weather.data.model.CityDataResponse
import com.test.weather.data.model.WeatherDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("geo/1.0/direct")
    suspend fun getCityData(
        @Query("q") city: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): List<CityDataResponse?>?

    @GET("data/3.0/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): WeatherDataResponse
}