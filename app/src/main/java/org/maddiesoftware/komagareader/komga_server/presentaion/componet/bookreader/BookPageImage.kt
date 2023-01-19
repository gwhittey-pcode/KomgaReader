package org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.komga_server.domain.BookPage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.BookReaderViewModel

@Composable
fun BookPageImage(
    bookPage: BookPage,
    id:String,
    modifier: Modifier = Modifier,
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

    LaunchedEffect(key1 = "tobitmap") {
        CoroutineScope(Dispatchers.IO).launch {
            bitmap.value = viewModel.uriToBitmap(
                context = context,
                uri = "${serverInfo.url}api/v1/books/${id}/pages/${bookPage.number}?zero_based=false"
            )
        }
    }

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
                    .pointerInput(key1 = "someStringKey?") {
                        detectTapGestures(
                            onTap = { it ->
                                val x = it.x
                                val y = it.y
                                Log.d("Pictouch","x = $x and y = $y")
                            }
                        )
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
            )
        } else {
            CircularProgressIndicator()
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
