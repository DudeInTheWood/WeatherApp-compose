package com.test.weather.ui.model.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.test.weather.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.weather.data.model.CurrentWeather
import com.test.weather.data.model.CityDataResponse
import com.test.weather.data.model.HourlyItem
import com.test.weather.data.model.WeatherDataResponse
import com.test.weather.ui.model.base.component.LoadingIndicator
import com.test.weather.ui.theme.BlueLightSky
import com.test.weather.ui.theme.BluePaleSky
import com.test.weather.ui.theme.WeatherAppTheme
import com.test.weather.ui.util.toTime
import kotlin.math.roundToInt

@Composable
fun ResultSector(
    modifier: Modifier = Modifier,
    weatherData: WeatherDataResponse?,
    selectedGeo: CityDataResponse?,
    isLoading: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            Box {
                LoadingIndicator(
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                )
            }
        } else if (weatherData != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f, fill = false),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = selectedGeo?.name.orEmpty(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "GPS",
                    tint = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "${weatherData.currentWeather?.temp?.roundToInt()}°",
                style = MaterialTheme.typography.displayLarge,
                color = Color.DarkGray
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "${stringResource(R.string.feel_like)} ${weatherData.currentWeather?.feelsLike?.roundToInt()}°",
                style = MaterialTheme.typography.titleLarge,
                color = Color.DarkGray
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = getWeatherDescription(weatherData.currentWeather),
                style = MaterialTheme.typography.titleMedium,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)) {
                HourlyWeatherList(
                    weatherData.hourly,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(BluePaleSky)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "NO DATA",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun HourlyWeatherList(
    data: List<HourlyItem>?,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier) {
        data?.let {
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(data) { hourItem ->
                    HourlyWeatherCard(
                        weather = WeatherHourlyModel(
                            temp = hourItem.temp?.roundToInt().toString(),
                            time = hourItem.dt?.toTime() ?: "",
                            imageUrl = conditionToIcon(hourItem)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(BlueLightSky)
                    )
                }
            }
        }
    }
}

private fun getWeatherDescription(current: CurrentWeather?): String {
    return current?.weather?.firstOrNull()?.description.orEmpty()
}

fun conditionToIcon(hourly: HourlyItem): String {
    val icon = hourly.weather?.firstOrNull()?.icon ?: return "unknown"
    return "https://openweathermap.org/img/wn/$icon@2x.png"
}

@Preview(showBackground = true)
@Composable
fun ResultSectorPreview() {
    WeatherAppTheme {
        ResultSector(
            weatherData = WeatherDataResponse(),
            selectedGeo = CityDataResponse(),
            isLoading = false
        )
    }
}