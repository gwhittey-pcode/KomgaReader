package org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
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
import com.mxalbert.zoomable.Zoomable
import com.mxalbert.zoomable.rememberZoomableState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.BookReaderScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.server_display.presentaion.activity.MainViewModule
import org.maddiesoftware.komagareader.server_display.presentaion.componet.MyAsyncImage
import org.maddiesoftware.komagareader.server_display.presentaion.componet.NavBar
import org.maddiesoftware.komagareader.server_display.presentaion.componet.NavDrawer
import org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.componets.BookPageImage
import org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.componets.BookPagesDialog
import org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.componets.BookReaderButton
import org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.componets.SeriesBooksDialog
import org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.util.BookPage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalPagerApi::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Destination
@Composable
fun BookReaderScreen(
    bookId: String,
    seriesId: String,
    viewModel: BookReaderViewModel = hiltViewModel(),
    mainViewModule: MainViewModule = hiltViewModel(),
//    viewModel: BookInfoViewModel = hiltViewModel(),
//    mainViewModule: MainViewModule = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val TOP_BAR_HEIGHT = 56.dp
    val configuration = LocalConfiguration.current
    val bookInfo = viewModel.state.bookInfo
    val pagesInfo = viewModel.state.pagesInfo
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.roundToPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.roundToPx() }
    val serverInfo = ServerInfoSingleton
    var pageNum: Int = 0
    var startPage: Int = 0
    if (bookInfo?.media?.pagesCount != null) {
        pageNum = bookInfo.media.pagesCount
    }
    if (bookInfo?.readProgress?.page != null) {
        startPage = bookInfo.readProgress.page
    }
    val libraryList = mainViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(startPage)
    val composableScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val showPageNavDialog = remember { mutableStateOf(false) }
    val showSeriesBookNavDialog = remember { mutableStateOf(false) }
    val showTopBar = remember { mutableStateOf(false) }
    val usePageSplit = remember { mutableStateOf(true) }
    val enabled by remember { mutableStateOf(true) }
    val overZoom by remember { mutableStateOf(false) }
    val fadeOut by remember { mutableStateOf(false) }
    var isOverlayVisible by remember { mutableStateOf(true) }
    val pagerPages = remember { mutableListOf<BookPage>() }
    var newIndexAdd: Int = 0
    var theCount: Int = 0
    if(usePageSplit.value){
        pagerPages.clear()
        pagesInfo?.forEachIndexed { index, page ->
            if (page.width!! > page.height!!) {
                val newBookPage = BookPage(
                    index = index + newIndexAdd,
                    doSplit = true,
                    pageName = "${page.number}A",
                    splitPart = "1",
                    number = page.number
                )
                val newBookPage2 = BookPage(
                    index = index + 1 + newIndexAdd,
                    doSplit = true,
                    pageName = "${page.number}B",
                    splitPart = "2",
                    number = page.number
                )
                newIndexAdd += 1
                pagerPages.add(newBookPage)
                pagerPages.add(newBookPage2)
            }else{
                val newBookPage = BookPage(
                    index = index + newIndexAdd,
                    doSplit = false,
                    pageName = "${page.number}",
                    number = page.number
                )
                pagerPages.add(newBookPage)
            }
        }
    }


    pagerPages.forEach(){
        Log.d("Pager","index = ${it.index} pageName = ${it.pageName} doSplit = ${it.doSplit}")
    }

    if (showPageNavDialog.value) {
        BookPagesDialog(
            setShowDialog = { showPageNavDialog.value = it },
            pagerPages = pagerPages,
            screenWidth = screenWidth,
            currentPage = pagerState.currentPage,
            onItemClick = {
                composableScope.launch {
                    pagerState.animateScrollToPage(it)
                    showPageNavDialog.value = false
                }

            }
        )
    }
    if (showSeriesBookNavDialog.value) {
        SeriesBooksDialog(
            setShowDialog = { showSeriesBookNavDialog.value = it },
            screenWidth = screenWidth,
            onItemClick = {
                showSeriesBookNavDialog.value = false
                navigator.navigate(
                    BookReaderScreenDestination(
                        bookId = it,
                        seriesId = bookInfo?.seriesId.toString()
                    )
                )
            }
        )
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            NavBar(
                modifier = Modifier.height(height = if (!showTopBar.value) 0.dp else 56.dp),
                onNavigationIconClick = { navigator.navigateUp() },
                onMenuItemClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavDrawer(
                libraryList = libraryList,
                viewModel = mainViewModule,
                onItemClick = { id ->
                    if (id == "home") {
                        navigator.navigate(HomeScreenDestination())
                    } else {
                        navigator.navigate(AllSeriesScreenDestination(libraryId = id))
                    }
                }
            )
        }
    ) {

        Log.d("stateTest", "bookID = ${bookInfo?.id}")
        Column(modifier = Modifier.fillMaxSize()) {
            theCount = if (usePageSplit.value){
                pagerPages.size
            }else{
                bookInfo?.media?.pagesCount?.toInt() ?: 0
            }
            HorizontalPager(
                count = theCount,
                state = pagerState,

                ) { page ->

                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (bookPageImg, nextPageButton, prevPageButton, dialogOpenPageNavButton, dialogOpenSeriesBookNavButton, dialogOpenTopBarButton) = createRefs()
                    val endGuideLine = createGuidelineFromStart(screenWidth.div(2))
                    val horGuideLine = createGuidelineFromTop(screenHeight.div(2))

                    val zoomState = rememberZoomableState(
                        minScale = if (overZoom) 0.5f else 1f,
                        maxScale = if (overZoom) 6f else 4f,
                        overZoomConfig = if (overZoom) OverZoomConfig(1f, 4f) else null
                    )
                    LaunchedEffect(pagerState) {
                        // Collect from the pager state a snapshotFlow reading the currentPage
                        snapshotFlow { pagerState.currentPage }.collect { page ->
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
                    Box(
                        modifier = Modifier
                            .constrainAs(bookPageImg) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    ) {


                        Zoomable(
                            modifier = Modifier.graphicsLayer {
                                clip = true
                                alpha = if (fadeOut) 1 - zoomState.dismissDragProgress else 1f
                            },
                            state = zoomState,
                            enabled = enabled,
                            onTap = { isOverlayVisible = !isOverlayVisible },
                            dismissGestureEnabled = true,
                            onDismiss = {

                                false
                            }
                        ) {
                            if (usePageSplit.value) {
                                Log.d("check","bookimage")
                                val bookPage = pagerPages[page]
                                BookPageImage(id = bookId, bookPage = bookPage)
                            } else {
                                MyAsyncImage(
                                    url = "${serverInfo.url}api/v1/books/${bookInfo?.id}/pages/${page}?zero_based=true",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .width(screenWidth)
                                        .height(screenHeight)

                                )
                            }
                        }
                    }
                    Button(//Right Side  Button to next page
                        enabled = true,
                        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .width(100.dp)
                            .alpha(0f)
                            .constrainAs(nextPageButton) {
                                start.linkTo(bookPageImg.end, (-100).dp)
                                top.linkTo(bookPageImg.top)
                                bottom.linkTo(bookPageImg.bottom)
                                height = Dimension.fillToConstraints

                            },
                        onClick = {
                            composableScope.launch {
                                if (page != pageNum - 1) {
                                    pagerState.animateScrollToPage(page + 1)
                                } else {
                                    Toast.makeText(context, "At Last Page", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }
                    ) {}//NextButton
                    Button(//Left Side Button for Next Page
                        enabled = true,
                        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .width(100.dp)
                            .alpha(0f)
                            .constrainAs(prevPageButton) {
                                start.linkTo(bookPageImg.start)
                                top.linkTo(bookPageImg.top)
                                bottom.linkTo(bookPageImg.bottom)
                                height = Dimension.fillToConstraints

                            },
                        onClick = {
                            composableScope.launch {
                                if (page != 0) {
                                    pagerState.animateScrollToPage(page - 1)
                                } else {
                                    Toast.makeText(context, "At First Page", Toast.LENGTH_LONG)
                                        .show()
                                }

                            }
                        }
                    ) {}//end prevButton

                    Button(//Lower Middle Button to Open Page Nav Dialog
                        enabled = true,
                        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .height(100.dp)
                            .alpha(0f)
                            .constrainAs(dialogOpenPageNavButton) {
                                start.linkTo(prevPageButton.end, 20.dp)
                                end.linkTo(nextPageButton.start, 20.dp)
                                bottom.linkTo(parent.bottom)
                                width = Dimension.fillToConstraints

                            },
                        onClick = { showPageNavDialog.value = true }
                    ) {}//End dialogOpenPageNavButton

                    Button(//Lower Middle Button to Open Page Nav Dialog
                        enabled = true,
                        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .height(100.dp)
                            .alpha(0f)
                            .constrainAs(dialogOpenSeriesBookNavButton) {
                                start.linkTo(prevPageButton.end, 20.dp)
                                end.linkTo(nextPageButton.start, 20.dp)
                                top.linkTo(parent.top)
                                width = Dimension.fillToConstraints

                            },
                        onClick = { showSeriesBookNavDialog.value = true }
                    ) {}//end dialogOpenSeriesBookNavButton

                    BookReaderButton(
//Lower Middle Button to Open Page Nav Dialog
                        enabled = true,
                        border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .height(200.dp)
                            .alpha(0f)
                            .constrainAs(dialogOpenTopBarButton) {
                                start.linkTo(prevPageButton.end, 20.dp)
                                end.linkTo(nextPageButton.start, 20.dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                width = Dimension.fillToConstraints

                            },
                        content = {},
                        onClick = { showTopBar.value = !showTopBar.value },
                        onLongClick = { Log.d("buttonClick", "onLong") },


                        ) //end dialogOpenSeriesBookNavButton

                }//End Constraint Layout

            }

        }

    }


}


