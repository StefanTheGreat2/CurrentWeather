package com.example.currentweather.usecase



data class ForecastDayUseCase(
    val date: String,
    val dateEpoch: Int,
    val day: DayUseCase,
    val astro: AstroUseCase,
    val hour: List<HourUseCase>,

    )

