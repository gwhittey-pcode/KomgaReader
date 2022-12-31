package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest


@Composable
fun MyAsyncImage(url:String = "", modifier: Modifier = Modifier) {
    val request: ImageRequest = ImageRequest.Builder(LocalContext.current.applicationContext)
        .data(url)
        .build()
    LocalContext.current.applicationContext.imageLoader.enqueue(request)
    AsyncImage(
        modifier = Modifier
            .height(300.dp)
            .width(195.dp),
        model = request,
        contentDescription = "None",
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopStart,


        )
}


