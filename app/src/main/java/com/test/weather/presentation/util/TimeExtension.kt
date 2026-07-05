package com.test.weather.presentation.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toTime(
    timeZoneId: String? = null,
    pattern: String = "HH:mm"
): String {
    return try {
        val instant = Instant.ofEpochSecond(this)
        val timeZone = timeZoneId?.let { ZoneId.of(it) } ?: ZoneId.systemDefault()
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
            .withZone(timeZone)
        formatter.format(instant)
    } catch (_: Exception) {
        "invalid time!"
    }
}
