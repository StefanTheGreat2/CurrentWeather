package com.example.currentweather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.currentweather.ui.components.networkImage.NetworkImage
import com.example.currentweather.ui.theme.BlackBackground
import com.example.currentweather.usecase.WeatherForecastUseCase
import com.example.currentweather.util.timeByDayMonth
import kotlin.math.roundToInt

@Composable
fun WeaklyContent(
    weatherForecastData: WeatherForecastUseCase, imageLoader: ImageLoader
) {
    Box {
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            weatherForecastData.forecastUseCase.forecastDay.forEach { day ->
                Box(
                    Modifier.padding(5.dp), contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(
                                shape = CircleShape.copy(
                                    CornerSize(15.dp)
                                )
                            )
                            .background(
                                BlackBackground.copy(0.1f), shape = CircleShape.copy(
                                    CornerSize(15.dp)
                                )
                            )
                            .padding(10.dp), contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = timeByDayMonth(day.date),
                                color = Color.White,
                                modifier = Modifier.padding(10.dp),
                                fontSize = MaterialTheme.typography.bodySmall.fontSize
                            )
                            NetworkImage(
                                imageUrl = day.day.condition.icon,
                                modifier = Modifier.size(100.dp),
                                imageLoader = imageLoader
                            )
                            Text(
                                text = "${day.day.mintempC.roundToInt()}C°",
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                            Text(
                                text = "Chance of Rain ${day.day.dailyChanceOfRain}%",
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize
                            )
                            Text(
                                text = day.day.condition.text,
                                color = Color.White,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize
                            )

                        }
                    }
                }

            }

        }

    }
}
