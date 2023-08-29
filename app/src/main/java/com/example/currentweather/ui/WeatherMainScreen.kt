package com.example.currentweather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.imageLoader
import coil.util.DebugLogger
import com.example.currentweather.R
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.data.remote.model.CurrentWeatherResponse
import com.example.currentweather.location.current_location.getCurrentLocation
import com.example.currentweather.location.permissions.LocationPermissionRequest
import com.example.currentweather.ui.components.TextSearchBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val imageLoader =
        LocalContext.current.imageLoader.newBuilder().logger(DebugLogger()).build()
    val isLoaded = remember { mutableStateOf(false) }
    val locationGranted = remember { mutableStateOf(false) }
    val context = LocalContext.current
    LocationPermissionRequest(context = context) {
        if (!locationGranted.value) {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                viewModel.getWeatherByDecimalDegree(getCurrentLocation(context))
                locationGranted.value = true
            }
        }
    }




    when (val result = viewModel.currentWeatherState.collectAsState().value) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(85.dp))
            }
        }
        is Resource.Success -> {
            isLoaded.value = true
            CurrentWeatherContent(result, viewModel, imageLoader)


        }
        else -> {

        }


    }
}


@Composable
fun CurrentWeatherContent(
    result: Resource.Success<CurrentWeatherResponse>,
    viewModel: MainViewModel,
    imageLoader: ImageLoader
) {
    val focusManager = LocalFocusManager.current
    ConstraintLayout(modifier = Modifier.pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus(true)
        })
    }) {
        val (locationName, currentTemp, condition, forecastBox, searchBar) = createRefs()
        val textValue = remember { mutableStateOf("") }

        Image(
            painter = painterResource(id = R.drawable.sunrise),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.Crop
        )
        Text(
            text = result.data?.location?.name!!.toString(),
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp, modifier = Modifier.constrainAs(locationName) {
                top.linkTo(parent.top, margin = 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = "${result.data.current?.tempC?.toInt().toString()}C°",
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 70.sp,
            modifier = Modifier.constrainAs(currentTemp) {
                bottom.linkTo(parent.bottom, margin = 80.dp)
                start.linkTo(parent.start, margin = 15.dp)

            }

        )
        Text(
            text = result.data.current?.condition?.text.toString(),
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            fontWeight = FontWeight.Light,
            fontSize = 30.sp,
            modifier = Modifier.constrainAs(condition) {
                top.linkTo(locationName.bottom, margin = 5.dp)
                start.linkTo(parent.start, margin = 15.dp)
                end.linkTo(parent.end, margin = 15.dp)

            }

        )
        TextSearchBar(modifier = Modifier.constrainAs(searchBar) {
            top.linkTo(condition.bottom, margin = 10.dp)
            start.linkTo(parent.start, margin = 50.dp)
            end.linkTo(parent.end, margin = 50.dp)
        },
            value = textValue.value,
            onValueChanged = {
                textValue.value = it
            },
            onSearchActionClick = {
                viewModel.getCurrentWeather(textValue.value)
                viewModel.getSevenDaysForecast(textValue.value)
            })
        Box(modifier = Modifier
            .constrainAs(forecastBox) {
                top.linkTo(searchBar.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(currentTemp.top, margin = 5.dp)
            }
        ) {
            Forecast(viewModel, imageLoader)

        }


    }


}
