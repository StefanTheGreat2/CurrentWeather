package com.example.currentweather.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrollViewModel @Inject constructor() : ViewModel() {
    var offset = 0f
    var columnHeight = -380f

    fun updateOffsetAndColumnHeight(dragAmount: Float) {
        viewModelScope.launch(Dispatchers.IO) {

            val newOffset = offset + dragAmount
            val newColumnHeight = columnHeight + dragAmount

            // Clamp the values
            offset = newOffset.coerceIn(-700f, 0f)
            columnHeight = newColumnHeight.coerceIn(-1000f, -380f)
        }
    }
}