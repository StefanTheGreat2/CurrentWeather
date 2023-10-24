package com.example.currentweather.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.currentweather.ui.components.WhiteText
import com.example.currentweather.ui.components.networkImage.NetworkImage
import com.example.currentweather.ui.theme.BlackBackground
import com.example.currentweather.usecase.ForecastDayUseCase
import com.example.currentweather.usecase.HourUseCase
import com.example.currentweather.usecase.WeatherForecastUseCase
import com.example.currentweather.util.currentMillisToHours
import com.example.currentweather.util.hourFormat
import com.example.currentweather.util.timeByDayMonth

@Composable
fun HourlyContent(weatherForecastData: WeatherForecastUseCase, imageLoader: ImageLoader) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()).padding(top=10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        weatherForecastData.forecastUseCase.forecastDay.forEachIndexed { i, day ->
            
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        color = BlackBackground.copy(0.1f), shape = CircleShape.copy(
                            CornerSize(25.dp)
                        )
                    )
                    .padding(10.dp)
            ) {
                day.hour.forEach { hour ->
                Log.i("TAG", "HourlyContent: ${currentMillisToHours(System.currentTimeMillis())}")
                Log.i("TAG", "HourlyContent: ${hourFormat(hour.time)}")
                    if (hourFormat(hour.time).toInt()> (currentMillisToHours(System.currentTimeMillis())) && i == 0) {
                        HourContent(hour, day, imageLoader)
                    } else if (i != 0) {
                        HourContent(hour = hour, day = day, imageLoader = imageLoader)
                    }
                }
            }
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Composable
private fun HourContent(
    hour: HourUseCase,
    day: ForecastDayUseCase,
    imageLoader: ImageLoader
) {
    Box(
        modifier = Modifier.padding(
            15.dp
        ), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WhiteText(
                text = hourFormat(hour.time)
            )
            WhiteText(text = timeByDayMonth(day.date))
            NetworkImage(
                modifier = Modifier.size(25.dp),
                imageUrl = hour.condition.icon,
                imageLoader
            )
            WhiteText(text = "${hour.chanceOfRain}%")
            WhiteText(text = hour.condition.text)

        }
    }
}

