package com.example.currentweather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.currentweather.ui.components.DetailGrid
import com.example.currentweather.ui.components.TextSearchBar
import com.example.currentweather.ui.components.networkImage.NetworkImage
import com.example.currentweather.ui.theme.BlackBackground
import com.example.currentweather.usecase.CurrentWeatherUseCase
import com.example.currentweather.usecase.WeatherForecastUseCase


@Composable
fun MainWeatherContent(
    currentWeatherData: CurrentWeatherUseCase,
    forecastWeatherData: WeatherForecastUseCase,
    imageLoader: ImageLoader,
    onSearch: (params: String) -> Unit,
    locationRequest: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    val textValue = remember { mutableStateOf("") }

    val composables = listOf<@Composable () -> Unit>(
        { TopSearchBar(currentWeatherData, imageLoader, textValue, onSearch) },
        { HourlyContent(forecastWeatherData, imageLoader) },
        { WeaklyContent(forecastWeatherData, imageLoader) },
        { DetailGrid(forecastWeatherData) },
    )
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus(true)
            })
        }
        .fillMaxSize()) {
        Column() {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .weight(1f)
            ) {
                composables.forEach {
                    it.invoke()
                }

            }
            Box(modifier = Modifier.weight(0.2f)) {
                BottomBar(currentWeatherData, locationRequest)
            }
        }
    }

}

@Composable
private fun BottomBar(
    currentWeatherData: CurrentWeatherUseCase,
    locationRequest: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = BlackBackground.copy(0.1f),
                shape = CircleShape.copy(CornerSize(25.dp))
            )
            .fillMaxSize()
            .padding(bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${currentWeatherData.current.tempC.toInt()}C°",
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.displayLarge.fontSize,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(10.dp)
        )
        FloatingActionButton(modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(10.dp),
            shape = CircleShape,
            onClick = {
                locationRequest()
            }) {
            Icon(Icons.Outlined.LocationOn, "Location Settings")
        }

    }
}

@Composable
private fun TopSearchBar(
    currentWeatherData: CurrentWeatherUseCase,
    imageLoader: ImageLoader,
    textValue: MutableState<String>,
    onSearch: (params: String) -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = BlackBackground.copy(0.1f),
                shape = CircleShape.copy(CornerSize(25.dp))
            )

    ) {
        NetworkImage(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopEnd),
            imageUrl = currentWeatherData.current.condition.icon,
            imageLoader = imageLoader
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (currentWeatherData.location.name),
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = currentWeatherData.current.condition.text,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            )


            TextSearchBar(modifier = Modifier.padding(top = 5.dp, bottom = 15.dp),
                value = textValue.value,
                onValueChanged = {
                    textValue.value = it
                },
                onSearchActionClick = {
                    onSearch(textValue.value)
                })

        }
    }
}
