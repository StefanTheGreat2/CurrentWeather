package com.example.currentweather.data.remote.model

import com.google.gson.annotations.SerializedName


data class WeatherForecastResponse (

    @SerializedName("location" ) var location : Location? = Location(),
    @SerializedName("current"  ) var current  : Current?  = Current(),
    @SerializedName("forecast" ) var forecast : Forecast? = Forecast()

)