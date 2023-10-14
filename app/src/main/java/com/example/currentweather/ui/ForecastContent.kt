package com.example.currentweather.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.currentweather.ui.components.DetailGrid
import com.example.currentweather.usecase.WeatherForecastUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun Forecast(
    weatherForecastData: WeatherForecastUseCase,
    imageLoader: ImageLoader,
    offsetCallback: (offset: Float) -> Unit,
) {

    val scope = rememberCoroutineScope()

    val offset = remember {
        AnimationState(0f)
    }
    val targetOffset = remember {
        mutableStateOf(0f)
    }
    val columnHeight = remember {
        AnimationState(-400f)
    }
    val targetColumnHeight = remember {
        mutableStateOf(-400f)
    }

    val transitionUpdate: () -> Unit = transitionUpdate(
        scope = scope,
        offset = offset,
        columnHeight = columnHeight,
        targetOffset = targetOffset,
        targetColumnHeight = targetColumnHeight,
        offsetCallback = offsetCallback,
    )

    val columScroll = rememberScrollState()

    val scrollableState =
        rememberScrollableState(scope, targetOffset, targetColumnHeight, transitionUpdate)

    Column(
        modifier = Modifier
            .scrollable(
                orientation = Orientation.Vertical, state = scrollableState, enabled = true
            )
            .verticalScroll(columScroll)
            .height((columnHeight.value.dp * (-1)))

    ) {
        HourlyContent(weatherForecastData, imageLoader)
        WeaklyContent(weatherForecastData, imageLoader)
        DetailGrid(weatherForecastData)
    }
}

@Composable
private fun rememberScrollableState(
    scope: CoroutineScope,
    targetOffset: MutableState<Float>,
    targetColumnHeight: MutableState<Float>,
    transitionUpdate: () -> Unit
): ScrollableState {
    val scrollableState = rememberScrollableState { delta ->

        scope.launch(Dispatchers.IO) {

            val validOffsetCheck = (targetOffset.value + delta)
            if (validOffsetCheck <= -700) {
                targetOffset.value = -700f
            } else if (validOffsetCheck <= 0) {
                targetOffset.value += delta

            } else {
                targetOffset.value = 0f
            }


            val validSizeCheck = (targetColumnHeight.value + delta)
            if (validSizeCheck <= -1000) {
                targetColumnHeight.value = -1000f
            } else if (validSizeCheck <= -400f) {
                targetColumnHeight.value += delta
            } else {
                targetColumnHeight.value = -400f
            }
        }
        transitionUpdate.invoke()
        delta
    }
    return scrollableState
}

@Composable
private fun transitionUpdate(
    scope: CoroutineScope,
    offset: AnimationState<Float, AnimationVector1D>,
    columnHeight: AnimationState<Float, AnimationVector1D>,
    targetOffset: MutableState<Float>,
    targetColumnHeight: MutableState<Float>,
    offsetCallback: (offset: Float) -> Unit,
): () -> Unit {
    val transitionUpdate: () -> Unit = {
        scope.launch(Dispatchers.IO) {

            offset.animateTo(
                targetValue = targetOffset.value,
                animationSpec = tween(delayMillis = 16),
                sequentialAnimation = !offset.isFinished
            ) {
                offsetCallback.invoke(this.value)
            }

        }
        scope.launch(Dispatchers.IO) {

            columnHeight.animateTo(
                targetValue = targetColumnHeight.value,
                animationSpec = tween(delayMillis = 16),
                sequentialAnimation = !columnHeight.isFinished
            )
        }

    }
    return transitionUpdate
}
