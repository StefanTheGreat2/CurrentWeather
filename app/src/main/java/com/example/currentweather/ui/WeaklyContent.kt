package com.example.currentweather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.data.remote.model.WeatherForecastResponse
import com.example.currentweather.ui.components.networkImage.NetworkImage
import com.example.currentweather.ui.theme.BlackBackground
import kotlin.math.roundToInt

@Composable
fun WeaklyContent(
    result: Resource.Success<WeatherForecastResponse>, imageLoader: ImageLoader
) {
    Box() {
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            result.data?.forecast?.forecastday?.forEach() { day ->
                Box(
                    Modifier.padding(5.dp), contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                BlackBackground.copy(0.6f), shape = CircleShape.copy(
                                    CornerSize(15.dp)
                                )
                            )
                            .clip(
                                shape = CircleShape.copy(
                                    CornerSize(15.dp)
                                )
                            )
                            .padding(10.dp), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            day.day?.condition?.icon
                            Text(
                                text = "${day.date}",
                                color = Color.White,
                                modifier = Modifier.padding(10.dp)
                            )
                            NetworkImage(
                                imageUrl = day.day?.condition?.icon!!,
                                modifier = Modifier.size(100.dp),
                                imageLoader = imageLoader
                            )
                            Text(
                                text = "${day.day?.mintempC?.roundToInt()}C°", color = Color.White
                            )
                            Text(
                                text = "Chance of Rain ${day.day?.dailyChanceOfRain}%",
                                color = Color.White
                            )
                            Text(text = "${day.day?.condition?.text}", color = Color.White)

                        }
                    }
                }

            }

        }
    }
}