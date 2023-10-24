package com.example.currentweather.ui.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.currentweather.R
import kotlinx.coroutines.*

@Composable
fun animateColors(isDay: MutableState<Boolean>, sunsetColor: Color, dayColor: Color) =
    if (!isDay.value) animateColorAsState(
        targetValue = sunsetColor, tween(2000)
    ) else animateColorAsState(targetValue = dayColor, tween(2000))

@Composable
fun animate(
    infiniteTransition: InfiniteTransition, initVal: Float, targetVal: Float, duration: Int
) = infiniteTransition.animateFloat(
    initialValue = initVal, targetValue = targetVal, animationSpec = infiniteRepeatable(
        animation = tween(duration, easing = LinearEasing), repeatMode = RepeatMode.Reverse
    )
)

@Composable
fun StarsAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.stars))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        contentScale = ContentScale.Crop,
        speed = 0.3f
    )


}

@Composable
fun BirdsAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.birds))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        contentScale = ContentScale.Crop,
        speed = 0.2f,
    )
}

@OptIn(DelicateCoroutinesApi::class)
fun animateCelestialBody(
    celestialBodyOffsetX: Animatable<Float, AnimationVector1D>,
    targetOffsetX: Float,
    celestialBodyOffsetY: Animatable<Float, AnimationVector1D>,
    targetOffsetY: Float,
    haloOffset: Animatable<Float, AnimationVector1D>,
    targetHaloOffset: Float,
    scope: CoroutineScope,
    isNight: Boolean = false,
    onNightIsOver: () -> Unit = {}
) {
    scope.launch(Dispatchers.IO) {

withContext(newSingleThreadContext("")){

        launch() {
            celestialBodyOffsetX.animateTo(targetOffsetX, tween(3000))
        }
        if (isNight) {
            launch {
                celestialBodyOffsetY.animateTo(700f, tween(3000))
            }.invokeOnCompletion {
                onNightIsOver()
            }
        }
        launch {
            if (isNight) delay(2000)
            celestialBodyOffsetY.animateTo(
                targetOffsetY, tween(2000)
            )
        }
        launch {
            if (isNight) delay(2000)
            haloOffset.animateTo(targetHaloOffset, tween(2000))
        }
    }
}
}
