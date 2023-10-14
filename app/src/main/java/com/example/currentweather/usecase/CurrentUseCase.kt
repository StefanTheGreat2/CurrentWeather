package com.example.currentweather.usecase



data class CurrentUseCase(
    val lastUpdatedEpoch: Long,
    val lastUpdated: String,
    val tempC: Double,
    val tempF: Double,
    val isDay: Int,
    val condition: ConditionUseCase,
    val windMph: Double,
    val windKph: Double,
    val windDegree: Int,
    val windDir: String,
    val pressureMb: Int,
    val pressureIn: Double,
    val precipMm: Double,
    val precipIn: Double,
    val humidity: Double,
    val cloud: Double,
    val feelslikeC: Double,
    val feelslikeF: Double,
    val visKm: Double,
    val visMiles: Double,
    val uv: Double,
    val gustMph: Double,
    val gustKph: Double
)
