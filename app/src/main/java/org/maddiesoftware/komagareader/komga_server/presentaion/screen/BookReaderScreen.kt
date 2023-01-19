package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mxalbert.zoomable.OverZoomConfig
import com.mxalbert.zoomable.rememberZoomableState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.BookReaderScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader.*
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.BookReaderViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalPagerApi::class, ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class
)
@Destination
@Composable
fun BookReaderScreen(
    bookId: String,
    seriesId: String,
    readListId: String? = null,
    groupType: String? = null,
    viewModel: BookReaderViewModel = hiltViewModel(),
    libraryViewModule: LibraryViewModule = hiltViewModel(),
    mainViewModel: MainViewModel,
    navigator: DestinationsNavigator,
) {
    val topBarHeight = 56.dp
    val configuration = LocalConfiguration.current
    val bookInfo = viewModel.state.bookInfo
    val pagesInfo = viewModel.state.pagesInfo
    val doingUpdateReadProgress = viewModel.state.doingUpdateReadProgress
    val useDblPageSplit = viewModel.state.useDblPageSplit
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.roundToPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.roundToPx() }
    val serverInfo = ServerInfoSingleton


    var pageNum: Int = 0
    var startPage = viewModel.state.startPage

    val libraryList = libraryViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(startPage)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val showPageNavDialog = remember { mutableStateOf(false) }
    val showSeriesBookNavDialog = remember { mutableStateOf(false) }
    val showTopBar = remember { mutableStateOf(false) }

    val enabled by remember { mutableStateOf(true) }
    val overZoom by remember { mutableStateOf(false) }
    val fadeOut by remember { mutableStateOf(false) }
    var isOverlayVisible by remember { mutableStateOf(true) }
    val pagerPages = viewModel.pagerPages
    var theCount by remember { mutableStateOf(0) }

    mainViewModel.showTopBar.value = false
    theCount = if (useDblPageSplit) {
        pagerPages.size
    } else {
        bookInfo?.media?.pagesCount?.toInt() ?: 0
    }
    if (!useDblPageSplit) {
        if (bookInfo?.media?.pagesCount != null) {
            pageNum = bookInfo.media.pagesCount
        }
    } else {
        pageNum = pagerPages.size
    }

    if (startPage > 0) {
        LaunchedEffect(pagerState) {
            Log.d("starCheck", "LaunchedEffect in startPage = $startPage")
            pagerState.scrollToPage(startPage)
        }
    }

    if (showPageNavDialog.value) {
        BookPagesDialog(setShowDialog = { showPageNavDialog.value = it },
            pagerPages = pagerPages,
            screenWidth = screenWidth,
            currentPage = pagerState.currentPage,
            onItemClick = {
                scope.launch {
                    pagerState.animateScrollToPage(it)
                    showPageNavDialog.value = false
                }
            })

    }

    if (showSeriesBookNavDialog.value) {
        if (groupType == "Read List") {
            ReadListBooksDialog(setShowDialog = { showSeriesBookNavDialog.value = it },
                screenWidth = screenWidth,
                onItemClick = {
                    showSeriesBookNavDialog.value = false
                    navigator.navigate(
                        BookReaderScreenDestination(
                            bookId = it,
                            readListId = readListId,
                            groupType = groupType,
                            seriesId = seriesId
                        )
                    )
                })
        } else {
            SeriesBooksDialog(setShowDialog = { showSeriesBookNavDialog.value = it },
                screenWidth = screenWidth,
                onItemClick = {
                    showSeriesBookNavDialog.value = false
                    navigator.navigate(
                        BookReaderScreenDestination(
                            bookId = it,
                            readListId = readListId,
                            groupType = groupType,
                            seriesId = seriesId
                        )
                    )
                })
        }

    }

    Log.d("stateTest", "bookID = ${bookInfo?.id}")
    Column(modifier = Modifier.fillMaxSize()) {
        if (theCount != 0) {
            HorizontalPager(
                count = theCount,
                state = pagerState,
            ) { page ->
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (bookPageImg, nextPageButton, prevPageButton, dialogOpenPageNavButton,
                        dialogOpenSeriesBookNavButton, dialogOpenTopBarButton) = createRefs()
                    val zoomState = rememberZoomableState(
                        minScale = if (overZoom) 0.5f else 1f,
                        maxScale = if (overZoom) 6f else 4f,
                        overZoomConfig = if (overZoom) OverZoomConfig(1f, 4f) else null
                    )

                    LaunchedEffect(pagerState) {
                        // Collect from the pager state a snapshotFlow reading the currentPage
                        snapshotFlow { pagerState.currentPage }.collect { page ->
                            val tmpPageCount = bookInfo?.media?.pagesCount
                            Log.d("LaunchedEffect", "LaunchedEffect")
                            Log.d("LaunchedEffect", "before If startPage=$startPage page=$page")
                            if (page > startPage) {
                                val bookNumber: Int? = if (useDblPageSplit) {
                                    pagerPages[page].number
                                } else {
                                    pagesInfo?.get(page)?.number
                                }
                                Log.d(
                                    "LaunchedEffect",
                                    "bookNumber=$bookNumber page=$page starPage = $startPage"
                                )
                                if (bookNumber != null) {
                                    if ((tmpPageCount != null) && (bookNumber.toInt() > startPage) && !doingUpdateReadProgress) {
                                        Log.d(
                                            "LaunchedEffect",
                                            "If startPage=$startPage page=$page"
                                        )
                                        viewModel.updateReadProgress(
                                            pagerIndex = page,
                                            newProgressPage = bookNumber,
                                            pageCount = tmpPageCount.toInt(),
                                            bookId = bookId
                                        )
                                    }
                                }
                            }


                            val targetScale = zoomState.minScale
                            zoomState.animateScaleTo(
                                targetScale = targetScale,
                                targetTranslation = if (zoomState.isZooming) {
                                    Offset.Zero
                                } else {
                                    Offset.Zero
                                }
                            )
                        }
                    }
                    Box(modifier = Modifier.constrainAs(bookPageImg) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }) {
//                        Zoomable(modifier = Modifier.graphicsLayer {
//                            clip = true
//                            alpha = if (fadeOut) 1 - zoomState.dismissDragProgress else 1f
//                        },
//                            state = zoomState,
//                            enabled = enabled,
//                            onTap = { isOverlayVisible = !isOverlayVisible },
//                            dismissGestureEnabled = true,
//                            onDismiss = {
//
//                                false
//                            })
//                        {
                        if (useDblPageSplit) {
                            Log.d("check", "bookimage")
                            val bookPage = pagerPages[page]
                            DblSplitBookImage(id = bookId, bookPage = bookPage)
                        } else {
                            NoSplitBookImage(bookPage = page, id =bookInfo?.id.toString() )
//                            MyAsyncImage(
//                                url = "${serverInfo.url}api/v1/books/${bookInfo?.id}/pages/${page}?zero_based=true",
//                                contentScale = ContentScale.FillBounds,
//                                modifier = Modifier
//                                    .width(screenWidth)
//                                    .height(screenHeight)
//                                    .pointerInput(key1 = "someStringKey?") {
//                                        detectTapGestures(
//                                            onTap = { it ->
//                                                val x = it.x
//                                                val y = it.y
//                                                Log.d("Pictouch", "2 x = $x and y = $y")
//                                            }
//                                        )
//                                    }
//                            )
                        }
//                        }
                    }
                    val endGuideLine = createGuidelineFromEnd(.1f)
                    BookReaderControlButtons(modifier = Modifier.constrainAs(nextPageButton) {
                        start.linkTo(bookPageImg.end)
                        top.linkTo(bookPageImg.top)
                        bottom.linkTo(bookPageImg.bottom)
                        end.linkTo(endGuideLine)
                        height = Dimension.fillToConstraints
                        width = (Dimension.percent(.1f))
                    }, onClickItem = {
                        scope.launch {
                            if (page != pageNum - 1) {
                                pagerState.animateScrollToPage(page + 1)
                            } else {
                                Toast.makeText(context, "At Last Page", Toast.LENGTH_LONG).show()
                            }
                        }
                    }) //end dialogOpenSeriesBookNavButton
                    BookReaderControlButtons(modifier = Modifier.constrainAs(prevPageButton) {
                        start.linkTo(bookPageImg.start)
                        top.linkTo(bookPageImg.top)
                        bottom.linkTo(bookPageImg.bottom)
                        height = Dimension.fillToConstraints
                        width = (Dimension.percent(.1f))
                    }, onClickItem = {
                        scope.launch {
                            if (page != 0) {
                                pagerState.animateScrollToPage(page - 1)
                            } else {
                                Toast.makeText(context, "At First Page", Toast.LENGTH_LONG).show()
                            }
                        }
                    })//End prevePAgeButton

                    BookReaderControlButtons(modifier = Modifier.constrainAs(
                        dialogOpenPageNavButton
                    ) {
                        start.linkTo(prevPageButton.end, 20.dp)
                        end.linkTo(nextPageButton.start, 20.dp)
                        bottom.linkTo(parent.bottom)
                        width = (Dimension.percent(.5f))
                    },
                        onClickItem = {
                            showPageNavDialog.value = true
                        })//End dialogOpenPageNavButton
                    BookReaderControlButtons(modifier = Modifier.constrainAs(
                        dialogOpenSeriesBookNavButton
                    ) {
                        start.linkTo(prevPageButton.end, 20.dp)
                        end.linkTo(nextPageButton.start, 20.dp)
                        top.linkTo(parent.top)
                        width = (Dimension.percent(.5f))
                    },
                        onClickItem = {
                            showSeriesBookNavDialog.value = true
                        }) //End dialogOpenSeriesBookNavButton
                    BookReaderControlButtons(
                        modifier = Modifier.constrainAs(dialogOpenTopBarButton) {
                            start.linkTo(prevPageButton.end, 20.dp)
                            end.linkTo(nextPageButton.start, 20.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = (Dimension.percent(.5f))
                        },
                        onClickItem = {
                            Log.d(
                                "showTopBar",
                                "showTopBar before = ${mainViewModel.showTopBar.value}"
                            )
                            mainViewModel.showTopBar.value = !mainViewModel.showTopBar.value
                            Log.d(
                                "showTopBar", "showTopB afafter = ${mainViewModel.showTopBar.value}"
                            )
                        },
                    )


                }//End Constraint Layout
            }
        }


    }
}

