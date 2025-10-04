package com.test.weather.ui.model.weather.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.test.weather.ui.theme.WeatherAppTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


data class WeatherHourlyModel(
    val temp: String = "",
    val time: String = "",
    val currentIcon: String = ""
)

@Composable
fun HourlyWeatherCard(
    weather: WeatherHourlyModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            weather.time, modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = Color.DarkGray
        )
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth()
        ) {
            WeatherIcon(
                modifier = Modifier
                    .heightIn(max = 60.dp)
                    .widthIn(max = 60.dp)
                    .clip(RoundedCornerShape(12.dp)),
                flag = weather.currentIcon
            )
        }
        Text(
            "${weather.temp}°",
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = Color.DarkGray
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HourlyWeatherCardPreview() {
    WeatherAppTheme {
        HourlyWeatherCard(WeatherHourlyModel())
    }
}