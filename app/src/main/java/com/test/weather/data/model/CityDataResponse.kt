package com.test.weather.data.model

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class CityDataResponse(

    @param:Json(name = "country")
    val country: String? = null,

    @param:Json(name = "name")
    val name: String? = null,

    @param:Json(name = "lon")
    val lon: Double? = null,

    @param:Json(name = "state")
    val state: String? = null,

    @param:Json(name = "lat")
    val lat: Double? = null
)
