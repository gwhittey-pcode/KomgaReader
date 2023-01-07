package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest


@Composable
fun MyAsyncImage(modifier: Modifier = Modifier, url: String = "") {
    val request: ImageRequest = ImageRequest.Builder(LocalContext.current.applicationContext)
        .data(url)
        .build()
    LocalContext.current.applicationContext.imageLoader.enqueue(request)
    AsyncImage(
        modifier = modifier,
        model = request,
        contentDescription = "None",
        contentScale = ContentScale.FillBounds,
        alignment = Alignment.TopStart,


        )
}


