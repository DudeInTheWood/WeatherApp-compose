package com.test.weather.ui.model.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weather.data.WeatherRepository
import com.test.weather.data.common.Resource
import com.test.weather.data.constant.ALEARTS
import com.test.weather.data.constant.DAILY
import com.test.weather.data.constant.MINUTELY
import com.test.weather.data.constant.UNITS_METRIC
import com.test.weather.data.model.CityDataResponse
import com.test.weather.data.model.WeatherDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository
) : ViewModel() {
    private val _state = MutableStateFlow(WeatherScreenState())
    val state: StateFlow<WeatherScreenState> = _state.asStateFlow()

    private val searchLimit = 5

    fun onSelectCity(city: CityDataResponse) {
        updateState { copy(selectedCity = city) }
        getWeatherData(lat = city.lat ?: 0.0, lon = city.lon ?: 0.0)
    }

    fun getCityData(city: String) {
        viewModelScope.launch {
            updateState { copy(isCityDataLoading = true, error = null) }
            val result = repo.fetchCityData(city, searchLimit)
            when (result) {
                is Resource.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        updateState {
                            copy(
                                isCityDataLoading = false,
                                cityList = null,
                                error = "No city Found"
                            )
                        }
                    } else {
                        updateState {
                            copy(
                                isCityDataLoading = false,
                                cityList = result.data,
                                error = null
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    updateState { copy(isCityDataLoading = false, error = result.message) }
                }
            }
        }
    }

    fun getWeatherData(lat: Double, lon: Double) {
        val exclude = "${MINUTELY},${DAILY},${ALEARTS}"
        val units = UNITS_METRIC

        viewModelScope.launch {
            updateState { copy(isWeatherDataLoading = true, error = null) }
            val result =
                repo.fetchWeatherData(lat = lat, lon = lon, exclude = exclude, units = units)

            when (result) {
                is Resource.Success -> {
                    updateState {
                        copy(
                            isWeatherDataLoading = false,
                            weatherData = result.data,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    updateState {
                        copy(
                            isWeatherDataLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    internal fun updateState(update: WeatherScreenState.() -> WeatherScreenState) {
        _state.update { it.update() }
    }
}


data class WeatherScreenState(
    val searchText: String = "",
    val cityList: List<CityDataResponse?>? = null,
    val selectedCity: CityDataResponse? = null,
    val weatherData: WeatherDataResponse? = null,
    val isCityDataLoading: Boolean = false,
    val isWeatherDataLoading: Boolean = false,
    val error: String? = null
)