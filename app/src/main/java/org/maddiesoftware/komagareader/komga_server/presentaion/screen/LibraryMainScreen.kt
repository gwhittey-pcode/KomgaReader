package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.maddiesoftware.komagareader.destinations.*
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.MainViewModule


@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
@Destination
fun LibraryMainScreen(
    navigator: DestinationsNavigator,
    mainViewModule: MainViewModule = hiltViewModel(),
    libraryId: String? = null,
){
    val libraryList = mainViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

//        Row {
//            LibraryMainTabs(selectedTabIndex = pagerState.currentPage,
//                onSelectedTab = {
//                    scope.launch {
//                        pagerState.animateScrollToPage(it.ordinal)
//                    }
//                })
//        }
//        Row {
//            HorizontalPager(
//                count = SeriesByIdTabPage.values().size,
//                state = pagerState
//            ) { index ->
//                Column(Modifier.fillMaxSize()) {
//                    when (index) {
//                        0 -> AllSeriesTab(
//                            onItemClick = {
//                                navigator.navigate(BookInfoScreenDestination(bookId = it))
//                            },
//
//                        )
//                        1 -> SeriesInfoTab()
//                    }
//                }
//            }
//        }

}