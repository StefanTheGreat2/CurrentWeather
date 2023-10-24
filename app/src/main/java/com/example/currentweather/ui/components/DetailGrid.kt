package com.example.currentweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.currentweather.R
import com.example.currentweather.ui.theme.BlackBackground
import com.example.currentweather.usecase.WeatherForecastUseCase
import kotlin.math.roundToInt


@Composable
fun DetailGrid(
    weatherForecastData: WeatherForecastUseCase
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val gridModifier: Modifier = Modifier.weight(1f)
        Row {
            DetailItem(
                modifier = gridModifier,
                name = "Feels like:",
                item = weatherForecastData.currentUseCase.feelslikeC.toString(),
                icon = R.drawable.feels_like
            )
            DetailItem(
                modifier = gridModifier,
                name = "Wind Speed:",
                item = weatherForecastData.currentUseCase.windKph.toString(),
                icon = R.drawable.wind_speed
            )
            DetailItem(
                modifier = gridModifier,
                name = "WindDirection:",
                item = weatherForecastData.currentUseCase.windDir,
                icon = R.drawable.compass
            )
        }

        Row() {
            DetailItem(
                modifier = gridModifier,
                name = "UV index:",
                item = weatherForecastData.currentUseCase.uv.toString(),
                icon = R.drawable.sun_icon
            )
            DetailItem(
                modifier = gridModifier,
                name = "Cloud Cover:",
                item = weatherForecastData.currentUseCase.cloud.toString(),
                icon = R.drawable.cloud_cover
            )
            DetailItem(
                modifier = gridModifier,
                name = "Pressure:",
                item = weatherForecastData.currentUseCase.pressureMb.toString(),
                icon = R.drawable.pressure
            )
        }
        Row(
        ) {
            DetailItem(
                modifier = gridModifier,
                name = "Humidity:",
                item = weatherForecastData.currentUseCase.humidity.roundToInt().toString()
                , icon = R.drawable.humidity
            )
            DetailItem(
                modifier = gridModifier,
                name = "Temp:",
                item = "${weatherForecastData.currentUseCase.tempC.roundToInt()}C",
                icon = R.drawable.feels_like
            )
            DetailItem(
                modifier = gridModifier,
                name = "Visibility:",
                item = weatherForecastData.currentUseCase.visKm.toString(),
                icon=R.drawable.visability
            )
        }
    }

}

@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    name: String,
    item: String,
    icon: Int = R.drawable.compass,
) {
    Box(
        modifier = modifier
            .size(150.dp)
            .padding(8.dp)
            .background(
                BlackBackground.copy(0.1f), shape = CircleShape.copy(
                    CornerSize(15.dp)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier=Modifier.align(Alignment.BottomEnd).size(100.dp).padding(10.dp)) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null, tint = Color.White
            )
        }
        Column(modifier=Modifier.align(Alignment.TopStart).padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
            Text(
                text = item,
                color = Color.White,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
        }
    }
}