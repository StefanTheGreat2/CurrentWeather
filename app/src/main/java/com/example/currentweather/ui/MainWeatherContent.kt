package com.example.currentweather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.ImageLoader
import com.example.currentweather.ui.components.TextSearchBar
import com.example.currentweather.ui.components.networkImage.NetworkImage
import com.example.currentweather.ui.theme.BlackBackground
import com.example.currentweather.usecase.CurrentWeatherUseCase
import com.example.currentweather.usecase.WeatherForecastUseCase
import kotlinx.coroutines.*


@Composable
fun CurrentWeatherContent(
    currentWeatherData: CurrentWeatherUseCase,
    forecastWeatherData: WeatherForecastUseCase,
    imageLoader: ImageLoader,
    onSearch: (params: String) -> Unit,
    locationRequest: () -> Unit,
) {
    val scope = CoroutineScope(Dispatchers.IO + CoroutineName("UIScope"))
    val focusManager = LocalFocusManager.current
    val offset = remember {
        mutableStateOf(0f)
    }
    val textValue = remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus(true)
            })
        }
        .fillMaxSize()) {
        val (topColumnInfo, bottomRowInfo, forecastBox) = createRefs()

        Box(modifier = Modifier
            .offset(y = offset.value.dp)
            .constrainAs(topColumnInfo) {
                top.linkTo(parent.top, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .background(
                color = BlackBackground.copy(0.1f),
                shape = CircleShape.copy(CornerSize(25.dp))
            )) {
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
                    fontSize = 40.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = currentWeatherData.current.condition.text,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontSize = 30.sp,
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

        Box(modifier = Modifier
            .constrainAs(forecastBox) {
                top.linkTo(topColumnInfo.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomRowInfo.top)
            }) {
            Forecast(forecastWeatherData, imageLoader) {
                scope.launch {
                    withContext(Dispatchers.IO) {
                        offset.value = it
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .constrainAs(bottomRowInfo) {
                    bottom.linkTo(parent.bottom, margin = 65.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .offset(y = (offset.value * (-1)).dp)
                .background(
                    color = BlackBackground.copy(0.1f),
                    shape = CircleShape.copy(CornerSize(25.dp))
                )
                .fillMaxWidth()
        ) {
            Text(
                text = "${currentWeatherData.current.tempC.toInt()}C°",
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 70.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            )
            FloatingActionButton(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
                shape = CircleShape,
                onClick = {
                    locationRequest()
                }) {
                Icon(Icons.Outlined.LocationOn, "Location Settings")
            }

        }

    }


}






