package com.example.currentweather.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun hourFormat(inputDate: String): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val outputFormat = "HH"
    val outputFormatter = DateTimeFormatter.ofPattern(outputFormat)
    val date = LocalDateTime.parse(inputDate, inputFormat)
    val formattedDate = date.format(outputFormatter)
    return formattedDate.toString()

}

fun timeByDayMonth(inputDate: String): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormat = "dd/MM"
    val outputFormatter = DateTimeFormatter.ofPattern(outputFormat)
    val date = LocalDate.parse(inputDate.trim(), inputFormat)
    val formattedDate = date.format(outputFormatter)
    return formattedDate.toString()
}

fun currentMillisToHours(millis: Long): Int {
    return ((millis / (1000 * 60 * 60)) % 24).toInt()
}
