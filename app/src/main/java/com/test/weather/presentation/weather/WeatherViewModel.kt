package com.test.weather.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weather.domain.common.AppResult
import com.test.weather.domain.usecase.GetWeatherUseCase
import com.test.weather.domain.usecase.SearchCitiesUseCase
import com.test.weather.presentation.weather.mapper.toUiModel
import com.test.weather.presentation.weather.model.CitySearchState
import com.test.weather.presentation.weather.model.CityUiModel
import com.test.weather.presentation.weather.model.WeatherContentState
import com.test.weather.presentation.weather.model.WeatherEvent
import com.test.weather.presentation.weather.model.WeatherScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WeatherScreenState())
    val state: StateFlow<WeatherScreenState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<WeatherEvent>()
    val events: SharedFlow<WeatherEvent> = _events.asSharedFlow()

    private val searchLimit = 5

    fun onSearchTextChange(searchText: String) {
        updateState { copy(searchText = searchText) }
    }

    fun onSelectCity(city: CityUiModel) {
        updateState { copy(selectedCity = city) }
        getWeatherData(city)
    }

    fun getCityData(city: String) {
        val query = city.trim()
        if (query.isBlank()) return

        viewModelScope.launch {
            updateState { copy(citySearchState = CitySearchState.Loading) }
            val result = searchCitiesUseCase(query, searchLimit)
            when (result) {
                is AppResult.Success -> {
                    val cityList = result.data.map { it.toUiModel() }
                    if (cityList.isEmpty()) {
                        updateState {
                            copy(
                                citySearchState = CitySearchState.Empty
                            )
                        }
                    } else {
                        updateState {
                            copy(
                                citySearchState = CitySearchState.Success(cityList)
                            )
                        }
                    }
                }

                is AppResult.Error -> {
                    val message = result.message
                    updateState {
                        copy(
                            citySearchState = CitySearchState.Error(message)
                        )
                    }
                    sendEvent(WeatherEvent.ShowSnackbar(message))
                }
            }
        }
    }

    private fun getWeatherData(city: CityUiModel) {
        viewModelScope.launch {
            updateState { copy(weatherState = WeatherContentState.Loading) }
            val result = getWeatherUseCase(lat = city.lat, lon = city.lon)

            when (result) {
                is AppResult.Success -> {
                    val weather = result.data.toUiModel(city)
                    updateState {
                        copy(
                            weatherState = WeatherContentState.Success(weather)
                        )
                    }
                }

                is AppResult.Error -> {
                    val message = result.message
                    updateState {
                        copy(
                            weatherState = WeatherContentState.Error(message)
                        )
                    }
                    sendEvent(WeatherEvent.ShowSnackbar(message))
                }
            }
        }
    }

    private fun updateState(update: WeatherScreenState.() -> WeatherScreenState) {
        _state.update { it.update() }
    }

    private suspend fun sendEvent(event: WeatherEvent) {
        _events.emit(event)
    }
}
