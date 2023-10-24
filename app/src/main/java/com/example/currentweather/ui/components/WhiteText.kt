package com.example.currentweather.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun WhiteText(text: String) {
    Text(text = text,
        color = Color.White,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize)
}