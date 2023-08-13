package com.example.currentweather.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.data.remote.model.WeatherForecastResponse
import com.example.currentweather.ui.components.WhiteText
import com.example.currentweather.ui.components.network_image.NetworkImage
import com.example.currentweather.util.hour

@Composable
fun HourlyContent(result: Resource.Success<WeatherForecastResponse>) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        val todayHourly = result.data?.forecast?.forecastday?.get(0)
        todayHourly?.hour?.forEach { hour ->
            Box(
                modifier = Modifier.padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    WhiteText(text = hour(hour.time.toString()))
                    NetworkImage(
                        modifier = Modifier.size(25.dp),
                        imageUrl = hour.condition?.icon.toString()
                    )
                    WhiteText(text = "${hour.chanceOfRain.toString()}%")
                    WhiteText(text = hour.condition?.text.toString())


                }
            }
        }
    }
}