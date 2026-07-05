package com.test.weather.presentation.weather.model

data class CityUiModel(
    val name: String,
    val country: String,
    val state: String?,
    val lat: Double,
    val lon: Double
) {
    val displayName: String
        get() = listOfNotNull(name, state, country)
            .filter { it.isNotBlank() }
            .joinToString(", ")
}
