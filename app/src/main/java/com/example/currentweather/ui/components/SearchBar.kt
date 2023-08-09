package com.example.currentweather.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onSearchActionClick: () -> Unit = {},
    onValueChanged: (String) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (!focusState.hasFocus) {
                    keyboardController?.hide()
                }
            }.padding(start = 50.dp, end = 50.dp),
        value = value,
        label = {
            Text(text = "Search", color = Color.White)
        },
        onValueChange = onValueChanged,
        leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.White) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchActionClick.invoke()
                keyboardController?.hide()
            }
        ), colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White, textColor = Color.White
        )
    )
}

