package org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.komga_server.domain.BookPage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.BookReaderViewModel

@Composable
fun BookPageThumb(
    bookPage: BookPage,
    id:String,
    modifier: Modifier = Modifier,
    imageHeight: Dp = 200.dp,
    imageWidth: Dp = 155.dp
){

    val viewModel: BookReaderViewModel = hiltViewModel()
    val bitmap = remember {  mutableStateOf<Bitmap?>(null) }
    val bookInfo = viewModel.state.bookInfo
    val serverInfo = ServerInfoSingleton
    val context = LocalContext.current

    LaunchedEffect(key1 = "bitmap") {
        CoroutineScope(Dispatchers.IO).launch {
            bitmap.value = viewModel.uriToBitmap(
                context = context,
                uri = "${serverInfo.url}api/v1/books/${bookInfo?.id}/pages/${bookPage.number}/thumbnail?zero_based=false"
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
                modifier = Modifier
//               .aspectRatio(size.width / size.height)
                    .width(imageWidth)
                    .height(imageHeight)

            )
        }
    } else {
        if(bitmap.value!= null) {
            Log.d("bitmap","width = ${bitmap.value!!.width}")
            Image(bitmap = bitmap.value!!.asImageBitmap(),contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = modifier
//               .aspectRatio(size.width / size.height)
                    .width(imageWidth)
                    .height(imageHeight)

            )
        } else {
            CircularProgressIndicator()
        }
    }

}