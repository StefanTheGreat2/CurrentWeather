package com.example.currentweather.ui.components.networkImage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.compose.AsyncImage


@Composable
fun NetworkImage(modifier: Modifier, imageUrl: String, imageLoader: ImageLoader) {

    AsyncImage(
        model = "http:$imageUrl",
        contentDescription = "",
        imageLoader = imageLoader,
        contentScale = ContentScale.Crop, modifier = modifier
    )
}