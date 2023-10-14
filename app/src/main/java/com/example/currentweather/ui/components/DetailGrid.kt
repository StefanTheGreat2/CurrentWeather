package com.example.currentweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                item = weatherForecastData.currentUseCase.feelslikeC.toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "Wind Speed:",
                item = weatherForecastData.currentUseCase.windKph.toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "WindDirection:",
                item = weatherForecastData.currentUseCase.windDir
            )
        }

        Row() {
            DetailItem(
                modifier = gridModifier,
                name = "UV index:",
                item = weatherForecastData.currentUseCase.uv.toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "Cloud Cover:",
                item = weatherForecastData.currentUseCase.cloud.toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "Pressure:",
                item = weatherForecastData.currentUseCase.pressureMb.toString()
            )
        }
        Row(
//            modifier = Modifier.padding(bottom = 50.dp)
        ) {
            DetailItem(
                modifier = gridModifier,
                name = "Humidity:",
                item = weatherForecastData.currentUseCase.humidity.roundToInt().toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "Temp:",
                item = "${weatherForecastData.currentUseCase.tempC.roundToInt()}C"
            )
            DetailItem(
                modifier = gridModifier,
                name = "Visibility:",
                item = weatherForecastData.currentUseCase.visKm.toString()
            )
        }
    }

}

@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    name: String,
    item: String
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = name, color = Color.White)
            Text(text = item, color = Color.White, fontSize = 25.sp)
        }
    }
}