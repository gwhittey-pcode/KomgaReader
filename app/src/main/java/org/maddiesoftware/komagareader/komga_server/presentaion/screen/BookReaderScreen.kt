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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.BookReaderScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookreader.*
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.BookReaderViewModel
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
   ExperimentalFoundationApi::class
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
    val configuration = LocalConfiguration.current
    val bookInfo = viewModel.state.bookInfo
    val pagesInfo = viewModel.state.pagesInfo
    val nextBookId = viewModel.state.nextBookId
    val prevBookId = viewModel.state.prevBookId
    val doingUpdateReadProgress = viewModel.state.doingUpdateReadProgress
    val useDblPageSplit = viewModel.state.useDblPageSplit
    val screenWidth = configuration.screenWidthDp.dp


    var pageNum: Int = 0
    val startPage = viewModel.state.startPage

    val libraryList = libraryViewModule.state.libraryList
    var goToPrevComic: Boolean = false
    var goToNextComic: Boolean = false
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val showPageNavDialog = remember { mutableStateOf(false) }
    val showSeriesBookNavDialog = remember { mutableStateOf(false) }
    val pagerPages = viewModel.pagerPages
    var theCount by remember(pagerPages.size,useDblPageSplit) { mutableStateOf(0) }


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
    val pagerState = rememberPagerState(
        initialPage = startPage,
        initialPageOffsetFraction = 0f
    ) {
        theCount
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
            ReadListBooksDialog(
                currentBookId = bookId,
                setShowDialog = { showSeriesBookNavDialog.value = it },
                screenWidth = screenWidth,
                onItemClick = {
                    showSeriesBookNavDialog.value = false
                    navigator.navigate(
                        BookReaderScreenDestination(
                            bookId = it,
                            readListId = readListId,
                            groupType = groupType,
                            seriesId = seriesId,
                        )
                    )
                })
        } else {
            SeriesBooksDialog(
                currentBookNumber = bookInfo?.number?.toInt() ?: 0,
                setShowDialog = { showSeriesBookNavDialog.value = it },
                screenWidth = screenWidth,
                onItemClick = {
                    showSeriesBookNavDialog.value = false
                    navigator.navigate(
                        BookReaderScreenDestination(
                            bookId = it,
                            readListId = readListId,
                            groupType = groupType,
                            seriesId = seriesId,
                        )
                    )
                })
        }

    }

    Log.d("stateTest", "bookID = ${bookInfo?.id}")
    Column(modifier = Modifier.fillMaxSize()) {
        if (theCount != 0) {
            HorizontalPager(

                state = pagerState,
            ) { page ->
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (bookPageImg, nextPageButton, prevPageButton, dialogOpenPageNavButton,
                        dialogOpenSeriesBookNavButton, dialogOpenTopBarButton) = createRefs()

                    LaunchedEffect(pagerState) {
                        // Collect from the pager state a snapshotFlow reading the currentPage
                        snapshotFlow { pagerState.currentPage }.collect { page ->
                            val tmpPageCount = bookInfo?.media?.pagesCount
                            if (page > startPage) {
                                val bookNumber: Int? = if (useDblPageSplit) {
                                    pagerPages[page].number
                                } else {
                                    pagesInfo?.get(page)?.number
                                }
                                if (bookNumber != null) {
                                    if ((tmpPageCount != null) && (bookNumber.toInt() > startPage) && !doingUpdateReadProgress) {
                                        Log.d("ReadProgress", "page#${bookNumber}")
                                        viewModel.updateReadProgress(
                                            pagerIndex = page,
                                            newProgressPage = bookNumber,
                                            pageCount = tmpPageCount.toInt(),
                                            bookId = bookId
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Box(modifier = Modifier.constrainAs(bookPageImg) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }) {
                        if (useDblPageSplit) {
                            Log.d("check", "bookimage")
                            val bookPage = pagerPages[page]
                            DblSplitBookImage(id = bookId, bookPage = bookPage)
                        } else {
                            NoSplitBookImage(bookPage = page, id = bookInfo?.id.toString())
                        }
                    }
                    val endGuideLine = createGuidelineFromEnd(.1f)

                    BookReaderControlButtons(modifier = Modifier.constrainAs(nextPageButton) {
                        start.linkTo(bookPageImg.end)
                        top.linkTo(bookPageImg.top)
                        bottom.linkTo(bookPageImg.bottom)
                        end.linkTo(endGuideLine)
                        height = Dimension.fillToConstraints
                        width = (Dimension.percent(.1f))
                    }) {
                        scope.launch {
                            if (page != pageNum - 1) {
                                pagerState.animateScrollToPage(page + 1)
                            } else {
                                if (!goToNextComic) {
                                    Toast.makeText(
                                        context,
                                        "At Last Page Click Again to go to Next Comic",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    goToNextComic = true

                                }else{
                                    if(nextBookId.toString() != "none") {
                                        navigator.navigate(
                                            BookReaderScreenDestination(
                                                bookId = nextBookId.toString(),
                                                readListId = readListId,
                                                groupType = groupType,
                                                seriesId = seriesId,
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    } //end dialogOpenSeriesBookNavButton

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


                                if (!goToPrevComic) {
                                    Toast.makeText(
                                        context,
                                        "At First Page Click Again to go to Prev Comic",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    goToPrevComic = true
                                }else{
                                    if(prevBookId.toString() != "none") {
                                        navigator.navigate(
                                            BookReaderScreenDestination(
                                                bookId = prevBookId.toString(),
                                                readListId = readListId,
                                                groupType = groupType,
                                                seriesId = seriesId,
                                            )
                                        )
                                    }

                                }

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
                            mainViewModel.showTopBar.value = !mainViewModel.showTopBar.value
                        },
                    )


                }//End Constraint Layout
            }
        }


    }
}

