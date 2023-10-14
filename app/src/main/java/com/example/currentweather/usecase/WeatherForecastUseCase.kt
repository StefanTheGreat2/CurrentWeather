package com.example.currentweather.usecase

import com.example.currentweather.data.remote.model.WeatherForecastResponse


data class WeatherForecastUseCase(
    val locationUseCase: LocationUseCase,
    val currentUseCase: CurrentUseCase,
    val forecastUseCase: ForecastUseCase,

    )


    fun mapToWeatherForecastUseCase(weatherForecastResponse: WeatherForecastResponse): WeatherForecastUseCase {
        val location = weatherForecastResponse.location?.let {
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

        val current = weatherForecastResponse.current?.let { current ->
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

        val forecastDayUseCase = weatherForecastResponse.forecast?.forecastday?.map { forecastDay ->
            val hourUseCases = forecastDay.hour.map { hour ->
                val hourCondition = hour.condition?.let {
                    ConditionUseCase(
                        text = it.text.orEmpty(),
                        icon = it.icon.orEmpty(),
                        code = it.code ?: 0
                    )
                }
                HourUseCase(
                    timeEpoch = hour.timeEpoch ?: 0,
                    time = hour.time.orEmpty(),
                    tempC = hour.tempC ?: 0.0,
                    tempF = hour.tempF ?: 0.0,
                    isDay = hour.isDay ?: 0,
                    condition = hourCondition ?: ConditionUseCase("", "", 0),
                    windMph = hour.windMph ?: 0.0,
                    windKph = hour.windKph ?: 0.0,
                    windDegree = hour.windDegree ?: 0,
                    windDir = hour.windDir.orEmpty(),
                    pressureMb = hour.pressureMb ?: 0.0,
                    pressureIn = hour.pressureIn ?: 0.0,
                    precipMm = hour.precipMm ?: 0.0,
                    precipIn = hour.precipIn ?: 0.0,
                    humidity = hour.humidity ?: 0.0,
                    cloud = hour.cloud ?: 0,
                    feelslikeC = hour.feelslikeC ?: 0.0,
                    feelslikeF = hour.feelslikeF ?: 0.0,
                    windchillC = hour.windchillC ?: 0.0,
                    windchillF = hour.windchillF ?: 0.0,
                    heatindexC = hour.heatindexC ?: 0.0,
                    heatindexF = hour.heatindexF ?: 0.0,
                    dewpointC = hour.dewpointC ?: 0.0,
                    dewpointF = hour.dewpointF ?: 0.0,
                    willItRain = hour.willItRain ?: 0,
                    chanceOfRain = hour.chanceOfRain ?: 0,
                    willItSnow = hour.willItSnow ?: 0,
                    chanceOfSnow = hour.chanceOfSnow ?: 0,
                    visKm = hour.visKm ?: 0.0,
                    visMiles = hour.visMiles ?: 0.0,
                    gustMph = hour.gustMph ?: 0.0,
                    gustKph = hour.gustKph ?: 0.0,
                    uv = hour.uv ?: 0.0
                )
            }

            val astroUseCase = AstroUseCase(
                sunrise = forecastDay.astro?.sunrise.orEmpty(),
                sunset = forecastDay.astro?.sunset.orEmpty(),
                moonrise = forecastDay.astro?.moonrise.orEmpty(),
                moonset = forecastDay.astro?.moonset.orEmpty(),
                moonPhase = forecastDay.astro?.moonPhase.orEmpty(),
                moonIllumination = forecastDay.astro?.moonIllumination.orEmpty(),
                isMoonUp = forecastDay.astro?.isMoonUp ?: 0,
                isSunUp = forecastDay.astro?.isSunUp ?: 0
            )

            val dayCondition = ConditionUseCase(
                text = forecastDay.day?.condition?.text.orEmpty(),
                icon = forecastDay.day?.condition?.icon.orEmpty(),
                code = forecastDay.day?.condition?.code ?: 0
            )

            val dayUseCase = DayUseCase(
                maxtempC = forecastDay.day?.maxtempC ?: 0.0,
                maxtempF = forecastDay.day?.maxtempF ?: 0.0,
                mintempC = forecastDay.day?.mintempC ?: 0.0,
                mintempF = forecastDay.day?.mintempF ?: 0.0,
                avgtempC = forecastDay.day?.avgtempC ?: 0.0,
                avgtempF = forecastDay.day?.avgtempF ?: 0.0,
                maxwindMph = forecastDay.day?.maxwindMph ?: 0.0,
                maxwindKph = forecastDay.day?.maxwindKph ?: 0.0,
                totalprecipMm = forecastDay.day?.totalprecipMm ?: 0.0,
                totalprecipIn = forecastDay.day?.totalprecipIn ?: 0.0,
                totalsnowCm = forecastDay.day?.totalsnowCm ?: 0,
                avgvisKm = forecastDay.day?.avgvisKm ?: 0.0,
                avgvisMiles = forecastDay.day?.avgvisMiles ?: 0,
                avghumidity = forecastDay.day?.avghumidity ?: 0,
                dailyWillItRain = forecastDay.day?.dailyWillItRain ?: 0,
                dailyChanceOfRain = forecastDay.day?.dailyChanceOfRain ?: 0,
                dailyWillItSnow = forecastDay.day?.dailyWillItSnow ?: 0,
                dailyChanceOfSnow = forecastDay.day?.dailyChanceOfSnow ?: 0,
                condition = dayCondition,
                uv = forecastDay.day?.uv ?: 0
            )

            ForecastDayUseCase(
                date = forecastDay.date.orEmpty(),
                dateEpoch = forecastDay.dateEpoch ?: 0,
                day = dayUseCase,
                astro = astroUseCase,
                hour = hourUseCases
            )
        }

        val forecastUseCase = ForecastUseCase(forecastDayUseCase ?: emptyList())

        return WeatherForecastUseCase(
            locationUseCase = location!!,
            currentUseCase = current!!,
            forecastUseCase = forecastUseCase
        )
    }
