package org.maddiesoftware.komagareader.komga_server.presentaion.componet.general

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest


@Composable
fun MyAsyncImage(
    modifier: Modifier = Modifier, url: String = "",
    contentScale: ContentScale =ContentScale.FillBounds
) {
    val request: ImageRequest = ImageRequest.Builder(LocalContext.current.applicationContext)
        .data(url)
        .memoryCacheKey(url)
        .diskCacheKey(url)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)

        .build()

    LocalContext.current.applicationContext.imageLoader.enqueue(request)
    SubcomposeAsyncImage(
        modifier = modifier,
        model = request,
        contentDescription = "None",
        contentScale = contentScale,
        alignment = Alignment.TopStart,
        ){

        val state = painter.state

        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            CircularProgressIndicator()
        } else {
            SubcomposeAsyncImageContent()

        }

    }
}


