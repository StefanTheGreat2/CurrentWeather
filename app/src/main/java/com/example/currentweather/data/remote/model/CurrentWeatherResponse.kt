package com.example.currentweather.data.remote.model

import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(

    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("current") var current: Current? = Current()

)