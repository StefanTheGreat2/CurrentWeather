package com.example.currentweather.data.remote.model

import com.google.gson.annotations.SerializedName


data class Forecast (

  @SerializedName("forecastday" ) var forecastday : ArrayList<ForecastDay> = arrayListOf()

)