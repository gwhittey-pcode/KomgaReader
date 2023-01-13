package org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.componets

import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
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
import org.maddiesoftware.komagareader.server_display.presentaion.componet.MyAsyncImage
import org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.BookReaderViewModel
import org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.util.BookPage

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
    val usePageSplit = false
    var pageName:String = ""
    var theCount: Int = 0
    if(!usePageSplit){
        theCount = if (bookInfo != null) {
            bookInfo.media?.pagesCount?.toInt()!!
        }else{
            pagerPages.size
        }
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

        Box() {
            LazyRow(
                modifier = Modifier
                    .width(screenWidth.minus(20.dp))
                    .height(250.dp)
                    .border(5.dp, GoldUnreadBookCount)
                    .align(Alignment.BottomStart),
                state = scrollState
            ) {
                items(theCount) { index ->
                    Card(
                        modifier = Modifier
                            .height(300.dp)
                            .width(155.dp)
                            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
                            .background(MaterialTheme.colors.surface)
                            .clickable {
                                onItemClick(index)
                            },
                        elevation = 5.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.surface),
//                        horizontalAlignment = Alignment.Start,
//                        verticalArrangement = Arrangement.Bottom,
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                if (usePageSplit) {
                                    val bookPage = pagerPages[index]
                                    BookPageThumb(
                                        bookPage = bookPage,
                                        id = bookInfo?.id.toString(),
                                        modifier = Modifier.align(Alignment.TopStart)
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
                                            .height(200.dp)
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
                                if(usePageSplit){
                                    val bookPage = pagerPages[index]
                                    pageName = bookPage.pageName
                                }else{
                                    pageName = index.and(1).toString()
                                }

                                Text(
                                    text = "Page  $pageName",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colors.onSecondary,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
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
        composableScope.launch {
            scrollState.scrollToItem(currentPage)
        }

    }

}

