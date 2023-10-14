package com.example.currentweather.usecase

import com.example.currentweather.data.remote.model.CurrentWeatherResponse


data class CurrentWeatherUseCase(

    val location: LocationUseCase,
    val current: CurrentUseCase,

    )

fun mapToCurrentWeatherUseCase(currentWeatherResponse: CurrentWeatherResponse): CurrentWeatherUseCase {
    val location = currentWeatherResponse.location?.let {
        LocationUseCase(
            name = it.name.orEmpty(),
            region = it.region.orEmpty(),
            country = it.country.orEmpty(),
            lat = it.lat ?: 0.0,
            lon = it.lon ?: 0.0,
            tzId = it.tzId.orEmpty(),
            localtimeEpoch = it.localtimeEpoch ?: 0,
            localtime = it.localtime.orEmpty()
        )
    }

    val current = currentWeatherResponse.current?.let { current ->
        val condition = current.condition?.let {
            ConditionUseCase(
                text = it.text.orEmpty(),
                icon = it.icon.orEmpty(),
                code = it.code ?: 0
            )
        }
        CurrentUseCase(
            lastUpdatedEpoch = current.lastUpdatedEpoch ?: 0,
            lastUpdated = current.lastUpdated.orEmpty(),
            tempC = current.tempC ?: 0.0,
            tempF = current.tempF ?: 0.0,
            isDay = current.isDay ?: 0,
            condition = condition ?: ConditionUseCase("", "", 0),
            windMph = current.windMph ?: 0.0,
            windKph = current.windKph ?: 0.0,
            windDegree = current.windDegree ?: 0,
            windDir = current.windDir.orEmpty(),
            pressureMb = current.pressureMb ?: 0,
            pressureIn = current.pressureIn ?: 0.0,
            precipMm = current.precipMm ?: 0.0,
            precipIn = current.precipIn ?: 0.0,
            humidity = current.humidity ?: 0.0,
            cloud = current.cloud ?: 0.0,
            feelslikeC = current.feelslikeC ?: 0.0,
            feelslikeF = current.feelslikeF ?: 0.0,
            visKm = current.visKm ?: 0.0,
            visMiles = current.visMiles ?: 0.0,
            uv = current.uv ?: 0.0,
            gustMph = current.gustMph ?: 0.0,
            gustKph = current.gustKph ?: 0.0
        )
    }
    return CurrentWeatherUseCase(location!!, current!!)
}