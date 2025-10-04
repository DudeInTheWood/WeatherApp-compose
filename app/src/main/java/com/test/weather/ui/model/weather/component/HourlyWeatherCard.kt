package com.test.weather.ui.model.weather.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.test.weather.R


data class WeatherHourlyModel(
    val temp: String = "",
    val time: String = "",
    val imageUrl: String = ""
)

@Composable
fun HourlyWeatherCard(
    weather: WeatherHourlyModel,
    modifier: Modifier = Modifier
) {
    val placeHolder = painterResource(R.drawable.ic_cloud)

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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(weather.imageUrl)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .scale(Scale.FILL)
                    .build(),
                placeholder = placeHolder,
                error = placeHolder,
                contentDescription = "Weather icon",
                modifier = Modifier.size(80.dp)
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

//@Preview(showBackground = true)
//@Composable
//fun HourlyWeatherCardPreview() {
//    WeatherAppTheme {
//        HourlyWeatherCard(WeatherHourlyModel())
//    }
//}