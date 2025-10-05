package com.test.weather.ui.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toTime(
    timeZone: ZoneId = ZoneId.of("GMT+7"),
    pattern: String = "HH:mm"
): String {
    return try {
        val instant = Instant.ofEpochSecond(this)
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
            .withZone(timeZone)
        formatter.format(instant)
    } catch (_: Exception) {
        "invalid time!"
    }
}