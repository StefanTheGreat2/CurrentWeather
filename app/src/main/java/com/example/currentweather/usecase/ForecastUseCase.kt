package com.example.currentweather.usecase



data class ForecastUseCase(
    val forecastDay: List<ForecastDayUseCase> = emptyList()

)

