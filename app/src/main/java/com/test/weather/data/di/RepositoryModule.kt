package com.test.weather.data.di

import com.test.weather.data.repository.OpenWeatherRepository
import com.test.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        repository: OpenWeatherRepository
    ): WeatherRepository
}
