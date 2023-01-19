package org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.komga_server.domain.BookPage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.BookReaderViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DblSplitBookImage(
    bookPage: BookPage,
    id:String,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    maxScale: Float = 1f,
    minScale: Float = 3f,
    contentScale: ContentScale = ContentScale.Fit,
    isRotation: Boolean = false,
    isZoomable: Boolean = true,
    scrollState: ScrollableState? = null
) {
    val viewModel: BookReaderViewModel = hiltViewModel()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.roundToPx() }
    val enabled by remember { mutableStateOf(true) }
    val fadeOut by remember { mutableStateOf(false) }
    var isOverlayVisible by remember { mutableStateOf(true) }
    val serverInfo = ServerInfoSingleton
    var doSplit: Boolean = false
    val context = LocalContext.current
    val bitmap = remember {  mutableStateOf<Bitmap?>(null)}

    val coroutineScope = rememberCoroutineScope()
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(1f) }
    val offsetY = remember { mutableStateOf(1f) }

    LaunchedEffect(key1 = "tobitmap") {
        CoroutineScope(Dispatchers.IO).launch {
            bitmap.value = viewModel.uriToBitmap(
                context = context,
                uri = "${serverInfo.url}api/v1/books/${id}/pages/${bookPage.number}?zero_based=false"
            )
        }
    }
    Box(
        modifier = Modifier
            .clip(shape)
            .background(backgroundColor)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { /* NADA :) */ },
                onDoubleClick = {
                    if (scale.value >= 2f) {
                        scale.value = 1f
                        offsetX.value = 1f
                        offsetY.value = 1f
                    } else scale.value = 3f
                },
            )
            .pointerInput(Unit) {
                if (isZoomable) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown()
                            do {
                                val event = awaitPointerEvent()
                                scale.value *= event.calculateZoom()
                                if (scale.value > 1) {
                                    scrollState?.run {
                                        coroutineScope.launch {
                                            setScrolling(false)
                                        }
                                    }
                                    val offset = event.calculatePan()
                                    offsetX.value += offset.x
                                    offsetY.value += offset.y
                                    rotationState.value += event.calculateRotation()
                                    scrollState?.run {
                                        coroutineScope.launch {
                                            setScrolling(true)
                                        }
                                    }
                                } else {
                                    scale.value = 1f
                                    offsetX.value = 1f
                                    offsetY.value = 1f
                                }
                            } while (event.changes.any { it.pressed })
                        }
                    }
                }
            }

    )
    {
    if (bookPage.doSplit) {
        var start = 0
        if(bitmap.value!= null){
            val width = bitmap.value!!.width
            val height = bitmap.value!!.height
            val newWidth = bitmap.value!!.width.div(2)
            start = if (bookPage.splitPart == "2"){
                newWidth
            }else{
                0
            }

            val newBitmap:Bitmap = Bitmap.createBitmap(bitmap.value!!, start, 0, newWidth, height)
            Image(bitmap = newBitmap.asImageBitmap(),contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = modifier
//               .aspectRatio(size.width / size.height)
                    .fillMaxSize()
                    .width(screenWidth)
                    .height(screenHeight)
                    .graphicsLayer {
                        if (isZoomable) {
                            scaleX = maxOf(maxScale, minOf(minScale, scale.value))
                            scaleY = maxOf(maxScale, minOf(minScale, scale.value))
                            if (isRotation) {
                                rotationZ = rotationState.value
                            }
                            translationX = offsetX.value
                            translationY = offsetY.value
                        }
                    }
            )
        }

    } else {
        if(bitmap.value!= null) {
            Log.d("bitmap","width = ${bitmap.value!!.width}")
            Image(bitmap = bitmap.value!!.asImageBitmap(),contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                modifier = Modifier
//               .aspectRatio(size.width / size.height)
                    .fillMaxSize()
                    .width(screenWidth)
                    .height(screenHeight)
                    .graphicsLayer {
                        if (isZoomable) {
                            scaleX = maxOf(maxScale, minOf(minScale, scale.value))
                            scaleY = maxOf(maxScale, minOf(minScale, scale.value))
                            if (isRotation) {
                                rotationZ = rotationState.value
                            }
                            translationX = offsetX.value
                            translationY = offsetY.value
                        }
                    }
            )
        } else {
            CircularProgressIndicator()
        }
    }
//        val myPainter = rememberAsyncImagePainter(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data("${serverInfo.url}api/v1/books/${id}/pages/${page}?zero_based=true")
//                .size(Size.ORIGINAL) // Set the target size to load the image at.
//                .build()
//        )
//
//        if (myPainter.state is AsyncImagePainter.State.Success) {
//            Image(
//                painter = myPainter,
//                contentDescription = "None",
//                contentScale = ContentScale.FillBounds,
//                modifier = Modifier
////                                        .aspectRatio(size.width / size.height)
//                    .fillMaxSize()
//                    .width(screenWidth)
//                    .height(screenHeight)
//            )
//        }
    }
}
suspend fun ScrollableState.setScrolling(value: Boolean) {
    scroll(scrollPriority = MutatePriority.PreventUserInput) {
        when (value) {
            true -> Unit
            else -> awaitCancellation()
        }
    }
}

