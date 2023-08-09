package com.example.currentweather.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun hour(inputDate: String): String {

    val outputFormat = "HH"
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val outputFormatter = DateTimeFormatter.ofPattern(outputFormat)

    val date = LocalDateTime.parse(inputDate, inputFormat)
    val formattedDate = date.format(outputFormatter)
    return formattedDate.toString()

}