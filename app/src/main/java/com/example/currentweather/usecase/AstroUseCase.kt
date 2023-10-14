package com.example.currentweather.usecase


data class AstroUseCase(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moonPhase: String,
    val moonIllumination: String,
    val isMoonUp: Int,
    val isSunUp: Int,

    )
