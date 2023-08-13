package com.example.currentweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentweather.data.remote.model.WeatherForecastResponse
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.data.remote.model.CurrentWeatherResponse
import com.example.currentweather.data.remote.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val currentWeather = MutableStateFlow<Resource<CurrentWeatherResponse>?>(Resource.Loading())
    val currentWeatherState: StateFlow<Resource<CurrentWeatherResponse>?> = currentWeather
    private val weatherForecast = MutableStateFlow<Resource<WeatherForecastResponse>?>(Resource.Loading())
    val weatherForecastResponseState: StateFlow<Resource<WeatherForecastResponse>?> = weatherForecast

init{
    getFiveDaysForecast("london")
    getCurrentWeather("london")
}
    fun getCurrentWeather(q:String) {
        viewModelScope.launch {
            weatherRepository.getWeather(q=q).collect{ weather ->
                currentWeather.value = weather
            }
        }
    }
    fun getFiveDaysForecast(city:String) {
        viewModelScope.launch {
            weatherRepository.getWeatherForecast(city).collect { _weatherForecast ->
                weatherForecast.value = _weatherForecast
            }
        }
    }
}

