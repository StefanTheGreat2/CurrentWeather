package com.example.currentweather.data.remote.repository

import androidx.annotation.WorkerThread
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.data.remote.api.WeatherService
import com.example.currentweather.data.remote.model.BaseResponse
import com.example.currentweather.data.remote.model.WeatherForecastResponse
import com.example.currentweather.data.remote.model.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherService: WeatherService) :
    BaseResponse() {

    @WorkerThread
    fun getWeather(q: String): Flow<Resource<CurrentWeatherResponse>> = flow {
        emit(safeApiCall { weatherService.getWeather(q = q) })

    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getWeatherForecast(city: String):Flow<Resource<WeatherForecastResponse>> = flow {
       emit(Resource.Loading())
        emit(safeApiCall {weatherService.getForecast(q=city)})
    }


}