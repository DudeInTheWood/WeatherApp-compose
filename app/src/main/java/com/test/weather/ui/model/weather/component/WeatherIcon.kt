package com.test.weather.ui.model.weather.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.test.weather.R

@Composable
fun WeatherIcon(modifier: Modifier = Modifier, flag: String) {
    val iconRes = when (flag) {
        "clear" -> R.drawable.ic_clear
        "cloudy" -> R.drawable.ic_cloud
        "rain" -> R.drawable.ic_rain
        "snow" -> R.drawable.ic_snow
        "thunderstorm" -> R.drawable.ic_thunder
        else -> R.drawable.ic_cloud
    }

    Icon(
        painter = painterResource(id = iconRes),
        contentDescription = flag,
        modifier = modifier.size(48.dp),
        tint = Color.Unspecified
    )
}