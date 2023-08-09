package com.example.currentweather.ui.components.network_image


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger


@Composable
fun NetworkImage(modifier: Modifier, imageUrl: String) {
    val imageLoader =
        LocalContext.current.imageLoader.newBuilder().logger(DebugLogger()).build()
    AsyncImage(
        model = "http:$imageUrl",
        contentDescription = "",
        imageLoader = imageLoader,
        contentScale = ContentScale.Crop, modifier = modifier
    )
}