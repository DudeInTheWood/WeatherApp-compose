package com.test.weather.presentation.weather.component

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.weather.R
import com.test.weather.presentation.common.component.LoadingIndicator
import com.test.weather.presentation.theme.BlueLightSky
import com.test.weather.presentation.theme.BluePaleSky
import com.test.weather.presentation.theme.WeatherAppTheme
import com.test.weather.presentation.weather.model.HourlyWeatherUiModel
import com.test.weather.presentation.weather.model.WeatherContentState
import com.test.weather.presentation.weather.model.WeatherUiModel

@Composable
fun ResultSector(
    modifier: Modifier = Modifier,
    weatherState: WeatherContentState
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (weatherState) {
            WeatherContentState.Empty -> {
                EmptyWeatherContent()
            }

            WeatherContentState.Loading -> {
                LoadingIndicator(
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }

            is WeatherContentState.Error -> {
                EmptyWeatherContent(message = weatherState.message)
            }

            is WeatherContentState.Success -> {
                WeatherContent(weatherData = weatherState.data)
            }
        }
    }
}

@Composable
private fun WeatherContent(weatherData: WeatherUiModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                text = weatherData.cityName,
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
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = weatherData.currentTemperature,
            style = MaterialTheme.typography.displayLarge,
            color = Color.DarkGray
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "${stringResource(R.string.feel_like)} ${weatherData.feelsLikeTemperature}",
            style = MaterialTheme.typography.titleLarge,
            color = Color.DarkGray
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = weatherData.description,
            style = MaterialTheme.typography.titleMedium,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            HourlyWeatherList(
                data = weatherData.hourlyForecast,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(BluePaleSky)
            )
        }
    }
}

@Composable
private fun EmptyWeatherContent(
    message: String = stringResource(R.string.no_data)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun HourlyWeatherList(
    data: List<HourlyWeatherUiModel>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (data.isNotEmpty()) {
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(data) { hourItem ->
                    HourlyWeatherCard(
                        weather = hourItem,
                        modifier = Modifier
                            .width(96.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(BlueLightSky)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultSectorPreview() {
    WeatherAppTheme {
        ResultSector(
            weatherState = WeatherContentState.Success(
                WeatherUiModel(
                    cityName = "Bangkok, TH",
                    currentTemperature = "31°",
                    feelsLikeTemperature = "34°",
                    description = "clear sky",
                    hourlyForecast = emptyList()
                )
            )
        )
    }
}
