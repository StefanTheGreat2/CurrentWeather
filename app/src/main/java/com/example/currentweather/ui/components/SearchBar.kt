package com.example.currentweather.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.currentweather.ui.theme.BlackBackground


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
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
            }
            .padding(start = 50.dp, end = 50.dp),
        shape = CircleShape.copy(CornerSize(30.dp)),
        value = value,
        label = {
            Text(text = "Search", color = Color.White.copy(0.2f))
        },
        onValueChange = onValueChanged,
        leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.White.copy(0.2f)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchActionClick.invoke()
                keyboardController?.hide()
            }
        ), colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = BlackBackground.copy(0.1f),
            focusedBorderColor = BlackBackground.copy(0.3f),
            unfocusedBorderColor = BlackBackground.copy(0.1f), focusedTextColor = Color.White
        )
    )
}

