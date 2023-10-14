package com.example.currentweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currentweather.data.remote.Resource
import com.example.currentweather.data.remote.repository.WeatherRepository
import com.example.currentweather.usecase.CurrentWeatherUseCase
import com.example.currentweather.usecase.WeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val currentWeather =
        MutableStateFlow<Resource<CurrentWeatherUseCase>?>(Resource.Loading())
    val currentWeatherState: StateFlow<Resource<CurrentWeatherUseCase>?> =
        currentWeather

    private val weatherForecast =
        MutableStateFlow<Resource<WeatherForecastUseCase>?>(Resource.Loading())
    val weatherForecastState: StateFlow<Resource<WeatherForecastUseCase>?> =
        weatherForecast

    private fun getCurrentWeather(params: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getCurrentWeather(params = params).collect { weather ->
                currentWeather.value = weather
            }
        }
    }

    private fun getSevenDaysForecast(params: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeatherForecast(params).collect { _weatherForecast ->
                weatherForecast.value = _weatherForecast
            }
        }
    }


    fun getWeather(locationInfo: String) {
        getCurrentWeather(locationInfo)
        getSevenDaysForecast(locationInfo)
    }
}

