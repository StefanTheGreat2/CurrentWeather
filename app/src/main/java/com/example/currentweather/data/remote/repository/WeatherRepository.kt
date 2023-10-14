package com.example.currentweather.data.remote.repository

import androidx.annotation.WorkerThread
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.data.remote.api.WeatherService
import com.example.currentweather.data.remote.model.BaseResponse
import com.example.currentweather.usecase.CurrentWeatherUseCase
import com.example.currentweather.usecase.WeatherForecastUseCase
import com.example.currentweather.usecase.mapToCurrentWeatherUseCase
import com.example.currentweather.usecase.mapToWeatherForecastUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherService: WeatherService) :
    BaseResponse() {

    @WorkerThread
    fun getCurrentWeather(params: String): Flow<Resource<CurrentWeatherUseCase>> =
        flow {
            emit(Resource.Loading())

            val weatherResponseResource = safeApiCall {
                weatherService.getWeather(params = params)
            }

            val currentWeatherUseCaseResource =
                Resource.transform(weatherResponseResource) { currentWeatherResponse ->
                    mapToCurrentWeatherUseCase(currentWeatherResponse)
                }

            emit(currentWeatherUseCaseResource)
        }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getWeatherForecast(params: String): Flow<Resource<WeatherForecastUseCase>> =
        flow {
            emit(Resource.Loading())

            val weatherResponseResource = safeApiCall {
                weatherService.getForecast(params = params)
            }

            val weatherForecastUseCaseResource =
                Resource.transform(weatherResponseResource) { weatherForecastResponse ->
                    mapToWeatherForecastUseCase(weatherForecastResponse)
                }

            emit(weatherForecastUseCaseResource)
        }.flowOn(Dispatchers.IO)
}