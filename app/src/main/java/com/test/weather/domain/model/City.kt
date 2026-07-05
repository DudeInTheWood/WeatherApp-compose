package com.test.weather.domain.model

data class City(
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
