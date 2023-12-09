package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.BookInfoScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.*
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.seriesbyid.SeriesByIdTabPage
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.seriesbyid.SeriesByIdTabs
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.seriesbyid.tabs.BooksFromSeriesTab
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.seriesbyid.tabs.SeriesInfoTab
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule
import androidx.compose.foundation.pager.HorizontalPager
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.librarymainscreen.LibraryMainTabPage


@OptIn( ExperimentalFoundationApi::class)
@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SeriesByIdScreen(
    seriesId: String? = null,
    navigator: DestinationsNavigator,
    libraryViewModule: LibraryViewModule = hiltViewModel(),
    mainViewModel: MainViewModel,
) {
    mainViewModel.showTopBar.value = true
    val libraryList = libraryViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val pageCount = SeriesByIdTabPage.entries.size
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        pageCount
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
    ) {
        Log.d("Tabs","Start Page Series by ID")
        Row {
            SeriesByIdTabs(selectedTabIndex = pagerState.currentPage,
                onSelectedTab = {
                    scope.launch {
                        pagerState.animateScrollToPage(it.ordinal)
                    }
                })
        }
        Row {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                Column(Modifier.fillMaxSize()) {
                    Log.d("Tabs","This Page = $page")
                    when (page) {
                        0 -> BooksFromSeriesTab(
                            onItemClick = {
                                navigator.navigate(BookInfoScreenDestination(bookId = it))
                            }
                        )
                        1 -> SeriesInfoTab()
                    }
                }

            }
        }
    }

}

/*
{ page ->
    Column(Modifier.fillMaxSize()) {
        when (page) {
            0 -> BooksFromSeriesTab(
                onItemClick = {
                    navigator.navigate(BookInfoScreenDestination(bookId = it))
                }
            )
            1 -> SeriesInfoTab()
        }
    }
}*/
