package org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.presentation.theme.GoldUnreadBookCount
import org.maddiesoftware.komagareader.komga_server.domain.BookPage
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.MyAsyncImage
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.BookReaderViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BookPagesDialog(
    setShowDialog: (Boolean) -> Unit,
    screenWidth: Dp,
    onItemClick: (page: Int) -> Unit,
    currentPage: Int,
    pagerPages: List<BookPage>,
//    pagerBooks:

) {
    val viewModel: BookReaderViewModel = hiltViewModel()
    val bookInfo = viewModel.state.bookInfo
    val serverInfo = ServerInfoSingleton
    val composableScope = rememberCoroutineScope()
    val scrollState = LazyListState()
    val usePageSplit = viewModel.state.useDblPageSplit
    var pageName:String = ""
    var theCount: Int = 0
    Log.d("usePageSplit", "usePageSplit = $usePageSplit")
    if(!usePageSplit){
         if (bookInfo != null) {
             theCount = bookInfo.media?.pagesCount?.toInt()!!
            Log.d("TopBookPagesDialog", "bookInfo")
        }
    }else{
        Log.d("TopBookPagesDialog", "pagerPages")
        theCount = pagerPages.size
    }

    Dialog(

        onDismissRequest = { setShowDialog(false) },
        properties = DestinationStyle.Dialog.properties.let {
            DialogProperties(
                dismissOnBackPress = it.dismissOnBackPress,
                dismissOnClickOutside = it.dismissOnClickOutside,
                securePolicy = it.securePolicy,
                usePlatformDefaultWidth = false
            )
        },
    ) {

        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            LazyRow(
                state = scrollState,
                modifier = Modifier
                    .width(screenWidth.minus(20.dp))
                    .height(280.dp)
//                    .border(5.dp, GoldUnreadBookCount)


            ) {
                Log.d("BookPagesDialog", "theCount = $theCount")
                items(theCount) { index ->
                    Card(
                        elevation = 5.dp,
                        modifier = Modifier
                            .height(265.dp)
                            .width(155.dp)
                            .padding(5.dp)
                            .background(MaterialTheme.colors.surface)
                            .clickable {
                                onItemClick(index)
                            },
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.surface),
//                        horizontalAlignment = Alignment.Start,
//                        verticalArrangement = Arrangement.Bottom,

                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                if (usePageSplit) {
                                    val bookPage = pagerPages[index]
                                    BookPageThumb(
                                        bookPage = bookPage,
                                        id = bookInfo?.id.toString(),
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .height(225.dp)
                                            .width(150.dp),
                                    )
                                } else {
                                    Log.d("dblCheck", "Running MyAsync")
                                    MyAsyncImage(
                                        url = "${serverInfo.url}api/v1/books/${bookInfo?.id}/pages/${
                                            index.plus(
                                                1
                                            )
                                        }/thumbnail",
                                        contentScale = ContentScale.FillBounds,
                                        modifier = Modifier
                                            .height(225.dp)
                                            .width(150.dp)
                                            .align(Alignment.TopStart)
                                    )
                                }
                            }
                            var boxColor: Color = MaterialTheme.colors.surface
                            if (currentPage == index) {
                                boxColor = GoldUnreadBookCount
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.Start)
                                    .background(boxColor)
                            ) {
                                if (usePageSplit){
                                    val bookPage = pagerPages[index]
                                    pageName = bookPage.pageName
                                }

                                if (!usePageSplit) {
                                    pageName = index.plus(1).toString()
                                }
                                Row(modifier = Modifier.align(Alignment.TopStart)){
                                    Spacer(modifier = Modifier.fillMaxWidth(.25f))
                                    Text(
                                        text = "Page  $pageName",
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colors.onSecondary,
                                        modifier = Modifier
                                            .padding(start = 5.dp, top = 2.dp),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                }


                            }
                        }
                    }
                }

            }
        }

        composableScope.launch {
            scrollState.scrollToItem(currentPage)
        }

    }

}

