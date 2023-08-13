package com.example.currentweather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.ui.components.DetailGrid


@Composable
fun Forecast(viewModel: MainViewModel = hiltViewModel()) {
    val isLoaded = remember {
        mutableStateOf(false)
    }
    when (val result = viewModel.weatherForecastResponseState.collectAsState().value) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(85.dp))
            }

        }
        is Resource.Success -> {
            isLoaded.value = true
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape.copy(CornerSize(15.dp)))
                    .height(350.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column {
                    HourlyContent(result)
                    WeaklyContent(result)
                    DetailGrid(result)
                }

            }
        }
        else -> {
            Text(text = "Unknown Error")
        }
    }

}



