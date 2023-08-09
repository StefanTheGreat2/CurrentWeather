package com.example.currentweather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.ui.components.DetailGrid
import com.example.currentweather.ui.theme.BlackBackground


@Composable
fun Forecast(viewModel: MainViewModel = hiltViewModel()) {
    val isLoaded = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        if (isLoaded.value.not())
            viewModel.getFiveDaysForecast("London")
    }
    when (val result = viewModel.weatherForecastResponseState.collectAsState().value) {
        is Resource.Loading -> {

        }
        is Resource.Success -> {
            isLoaded.value = true
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape.copy(CornerSize(15.dp)))
                    .height(350.dp)
                    .background(BlackBackground.copy(0.6f))
                    .verticalScroll(rememberScrollState())
            ) {
                Column {
                    HourlyContent(result)
                    WeaklyContent(result)
                    DetailGrid(result)
                }

            }
        }
        else -> {}
    }

}



