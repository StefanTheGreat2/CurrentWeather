package com.example.currentweather.ui.animation


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.currentweather.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext


@Composable
fun DayToSunsetBackGround(content: @Composable () -> Unit = {}, switchToSunset: Boolean) {
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()
    val isDay = remember {
        mutableStateOf(true)
    }
    val seaMovement by animate(infiniteTransition, 0.01f, 0.2f, 5000)
    val sandMovement by animate(
        infiniteTransition,
        0.3f,
        0.4f,
        10000
    )

    val skyDay by animateColors(
        isDay,
        Color(0xFF070D2E),
        Color(0xff4fa6f7)
    )
    val horizonDay by animateColors(
        isDay,
        Color(0xFFF3BC4F),
        Color(0xff1c36b8).copy(0.3f)
    )
    val sandColor by animateColors(
        isDay,
        Color(0xFF977021),
        Color(0xFFF1C977)
    )


    val skyColors = arrayOf(
        0.1f to skyDay,
        0.3f to if (isDay.value) skyDay else Color(0xFF11216F),
        0.9f to horizonDay,
    )

    val earthColors = arrayOf(
        seaMovement to Color(0xff1c36b8),
        sandMovement to sandColor,
        1f to Color(0xfff2eac2),
    )
    val sunColors = arrayOf(
        0.1f to Color(0xFFFFEB3B),
        0.3f to Color(0xFFFFD200),
        0.8f to Color(0xFFFFE717).copy(0.2f),
        1f to Color(0xFFFFE500).copy(0.01f),
    )
    val sunHaloColors = arrayOf(
        0.8f to Color(0xFFFFE717).copy(0.2f),
        1f to Color(0xFFFFE500).copy(0.01f),
    )
    val skyBrush = Brush.verticalGradient(colorStops = skyColors)
    val earthBrush = Brush.verticalGradient(colorStops = earthColors)
    val sunBrush = Brush.radialGradient(colorStops = sunColors)
    val haloBrush = Brush.radialGradient(colorStops = sunHaloColors)

    val sunOffsetX = remember { Animatable(0f) }
    val sunOffsetY = remember { Animatable(0f) }
    val haloRotation by animate(
        infiniteTransition,
        180f,
        200f,
        20000
    )
    val haloOffsetY = remember { Animatable(80f) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(brush = skyBrush)
                    .weight(0.3f)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    if (!isDay.value) {
                        StarsAnimation()
                    }
                }
                if (isDay.value) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        BirdsAnimation()

                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .offset(x = (25).dp, y = 13.5.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.palms),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .offset(x = sunOffsetX.value.dp, y = sunOffsetY.value.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(sunBrush)
                            .size(200.dp)
                            .align(Alignment.TopEnd)
                    ){
                        Image(painter = painterResource(id = R.drawable.sun_background), contentDescription = null, alpha = 0.8f, contentScale = ContentScale.Crop)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .offset(x = sunOffsetX.value.dp, y = sunOffsetY.value.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(350.dp)
                            .background(Color.Transparent)
                            .align(Alignment.TopEnd)
                            .rotate(haloRotation)
                            .offset(y = haloOffsetY.value.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(haloBrush)
                                .size(250.dp)
                                .padding(15.dp)
                        )
                    }

                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(earthBrush)
                    .weight(0.1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sand),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alpha = 0.2f
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.islandsand),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 50.dp)
                    )
                }
            }
        }
    }
    if (switchToSunset) {
        LaunchedEffect(Unit) {
            delay(1000)
            animateCelestialBody(
                celestialBodyOffsetX = sunOffsetX,
                targetOffsetX = -180f,
                celestialBodyOffsetY = sunOffsetY,
                targetOffsetY = 440f,
                haloOffset = haloOffsetY,
                targetHaloOffset = 180f,
                scope = scope
            )
            isDay.value = !isDay.value
        }
    }
    content()
}
