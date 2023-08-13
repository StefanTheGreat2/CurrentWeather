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
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.data.remote.model.WeatherForecastResponse
import com.example.currentweather.ui.theme.BlackBackground
import kotlin.math.roundToInt


@Composable
fun DetailGrid(
    result: Resource<WeatherForecastResponse>
) {
    Column(
        modifier = Modifier
            .padding(0.dp, 15.dp, 0.dp, 0.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val gridModifier: Modifier = Modifier.weight(1f)
        Row {
            DetailItem(
                modifier = gridModifier,
                name = "Feels like:",
                item = result.data?.current?.feelslikeC!!.toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "Wind Speed:",
                item = result.data.current?.windKph!!.toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "WindDirection:",
                item = result.data.current?.windDir!!.toString()
            )
        }

        Row {
            DetailItem(
                modifier = gridModifier,
                name = "UV index:",
                item = result.data?.current?.uv!!.toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "Cloud Cover:",
                item = result.data.current?.cloud!!.toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "Pressure:",
                item = result.data.current?.pressureMb!!.toString()
            )
        }
        Row {
            DetailItem(
                modifier = gridModifier,
                name = "Humidity:",
                item = result.data?.current?.humidity!!.roundToInt().toString()
            )
            DetailItem(
                modifier = gridModifier,
                name = "Temp:",
                item = "${result.data.current?.tempC!!.roundToInt()}C"
            )
            DetailItem(
                modifier = gridModifier,
                name = "Visibility:",
                item = result.data.current?.visKm!!.toString()
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
                BlackBackground.copy(0.6f), shape = CircleShape.copy(
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