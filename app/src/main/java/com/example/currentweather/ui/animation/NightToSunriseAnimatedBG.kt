package com.example.currentweather.ui.animation

import androidx.compose.animation.core.Animatable
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


@Composable
fun NightToSunriseBackground(content: @Composable () -> Unit = {}, switchToSunrise: Boolean) {
    val scope = rememberCoroutineScope()
    val isNight = remember {
        mutableStateOf(true)
    }

    val skyDay by animateColors(
        isNight,
        Color(0xFF0C1244),
        Color(0xFF060918)
    )
    val horizonDay by animateColors(
        isNight,
        Color(0xFF685034),
        Color(0xFF091344)
    )
    val sandColor by animateColors(
        isNight,
        Color(0xFF1C442C),
        Color(0xFF362D19)
    )


    val skyColors = arrayOf(
        0.1f to skyDay,
        0.3f to if (!isNight.value) Color(0xFF320670) else Color(0xFF11216F),
        0.9f to horizonDay,
    )

    val earthColors = arrayOf(
        0.2f to Color(0xFF0D3D13),
        0.4f to sandColor,
        1f to Color(0xFF3F3A20),
    )
    val moonColors = arrayOf(
        0.1f to Color(0xFF858282),
        0.3f to Color(0xFFBDBDBA),
        0.5f to Color(0xFFFFFFFF).copy(0.01f),
    )
    val moonHaloColors = arrayOf(
        0.8f to Color(0xFFFFFFFF).copy(0.2f),
        1f to Color(0xFFFFFFFF).copy(0.01f),
    )
    val sunColors = arrayOf(
        0.1f to Color(0xFFF44336),
        0.3f to Color(0xFFFFD200),
        0.8f to Color(0xFFE91E63).copy(0.2f),
        1f to Color(0xFFFFE500).copy(0.01f),
    )
    val sunHaloColors = arrayOf(
        0.8f to Color(0xFFFFE717).copy(0.2f),
        1f to Color(0xFFFF5722).copy(0.01f),
    )
    val skyBrush = Brush.verticalGradient(colorStops = skyColors)
    val earthBrush = Brush.verticalGradient(colorStops = earthColors)
    val moonBrush = Brush.radialGradient(colorStops = moonColors)
    val sunBrush = Brush.radialGradient(colorStops = sunColors)
    val moonHaloBrush = Brush.radialGradient(colorStops = moonHaloColors)
    val sunHaloBrush = Brush.radialGradient(colorStops = sunHaloColors)
    val haloOffsetY = remember { Animatable(35f) }
    val haloRotation by remember {
        mutableStateOf(50f)
    }

    val celestialBodyOffsetX = remember { Animatable(0f) }
    val celestialBodyOffsetY = remember { Animatable(0f) }

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
                        .height(700.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    if (isNight.value) {
                        StarsAnimation()
                    }
                }
                if (!isNight.value) {
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
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .offset(
                            x = celestialBodyOffsetX.value.dp, y = celestialBodyOffsetY.value.dp
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isNight.value) moonBrush else sunBrush)
                            .size(200.dp)
                            .align(Alignment.TopEnd), contentAlignment = Alignment.Center
                    ) {
                        if (isNight.value) {
                            Image(
                                painter = painterResource(id = R.drawable.moon),
                                contentDescription = "",
                                modifier = Modifier.size(90.dp),
                                contentScale = ContentScale.Crop,
                                alpha = 0.7f
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .offset(
                            x = celestialBodyOffsetX.value.dp, y = celestialBodyOffsetY.value.dp
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .background(Color.Transparent)
                            .align(Alignment.TopEnd)
                            .rotate(haloRotation)
                            .offset(y = haloOffsetY.value.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isNight.value) moonHaloBrush else sunHaloBrush)
                                .size(150.dp)
                                .padding(15.dp)
                        )
                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mountain),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                    )

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
            }
        }
    }
    if (switchToSunrise) {
        LaunchedEffect(Unit) {
            delay(1000)
            animateCelestialBody(
                celestialBodyOffsetX = celestialBodyOffsetX,
                targetOffsetX = -80f,
                celestialBodyOffsetY = celestialBodyOffsetY,
                targetOffsetY = 370f,
                haloOffset = haloOffsetY,
                targetHaloOffset = 35f,
                scope = scope,
                isNight = isNight.value
            ) {
                isNight.value = !isNight.value
            }
        }
    }

    content()
}
