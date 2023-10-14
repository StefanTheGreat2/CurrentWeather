package com.example.currentweather.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.imageLoader
import coil.util.DebugLogger
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.location.current.getCurrentLocation
import com.example.currentweather.location.permissions.LocationPermissionRequest
import com.example.currentweather.ui.animation.DayToSunsetBackGround
import com.example.currentweather.ui.animation.NightToSunriseBackground
import com.example.currentweather.ui.components.TextSearchBar
import com.example.currentweather.util.currentMillisToHours
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val imageLoader = context.imageLoader.newBuilder().logger(DebugLogger()).build()
    val locationGranted = remember { mutableStateOf(false) }

    if (!locationGranted.value) {
        LocationPermissionRequest(context = context) {
            locationGranted.value = true
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                viewModel.getWeather(getCurrentLocation(context))
            }
        }
    }

    BackgroundImage()

    val currentWeather = viewModel.currentWeatherState.collectAsState().value
    val weatherForecast = viewModel.weatherForecastState.collectAsState().value
    when {
        currentWeather is Resource.Loading || weatherForecast is Resource.Loading -> {
            LoadingContent()
        }
        currentWeather is Resource.Success && weatherForecast is Resource.Success -> {
            CurrentWeatherContent(
                imageLoader = imageLoader,
                currentWeatherData = currentWeather.data,
                forecastWeatherData = weatherForecast.data, onSearch = { params ->
                    viewModel.getWeather(params)
                }, locationRequest = {
                    viewModel.viewModelScope.launch(Dispatchers.IO) {
                        viewModel.getWeather(getCurrentLocation(context))
                    }
                }
            )
        }
        currentWeather is Resource.Error || weatherForecast is Resource.Error -> {
            ErrorContent { viewModel.getWeather(it) }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(onSearch: (params: String) -> Unit) {
    val textValue = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Something went wrong\n" + "\n Try to search again...".trim(),
            color = Color.White,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(50.dp),
        )
        TextSearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 150.dp),
            value = textValue.value,
            onValueChanged = {
                textValue.value = it
            },
            onSearchActionClick = {
                onSearch(textValue.value)
            },
        )
        CircularProgressIndicator(
            Modifier
                .size(80.dp)
                .align(Alignment.Center)
        )

    }
}

@Composable
private fun BackgroundImage() {
    Log.i("TAG", "BackgroundImage: " + currentMillisToHours(System.currentTimeMillis()))
    when (currentMillisToHours(System.currentTimeMillis())) {

        in 5..7 -> NightToSunriseBackground(switchToSunrise = true)
        in 7..17 -> DayToSunsetBackGround(switchToSunset = false)
        in 18..19 -> DayToSunsetBackGround(switchToSunset = true)
        in 20..24 -> NightToSunriseBackground(switchToSunrise = false)
        else -> NightToSunriseBackground(switchToSunrise = false)
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BackgroundImage()
}
