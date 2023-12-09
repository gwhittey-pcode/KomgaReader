package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.CollectionByIdScreenDestination
import org.maddiesoftware.komagareader.destinations.ReadListByIdScreenDestination
import org.maddiesoftware.komagareader.destinations.SeriesByIdScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.librarymainscreen.LibraryMainTabPage
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.librarymainscreen.LibraryMainTabs
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.librarymainscreen.tabs.AllCollectionsTab
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.librarymainscreen.tabs.AllReadListTab
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.librarymainscreen.tabs.AllSeriesTab
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule


@OptIn(
    ExperimentalFoundationApi::class
)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun LibraryMainScreen(
    libraryId: String? = null,
    libraryViewModule: LibraryViewModule = hiltViewModel(),
    mainViewModel: MainViewModel,
    navigator: DestinationsNavigator,
) {
    mainViewModel.showTopBar.value = true
    var libraryName = ""
    libraryViewModule.state.libraryList?.forEach {
        if (libraryId == it.id) libraryName = it.name.toString()
    }
    mainViewModel.topBarTitle.value = "$libraryName Main Page"
    val libraryList = libraryViewModule.state.libraryList
    val pageCount = LibraryMainTabPage.entries.size
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        pageCount
    }
    val scope = rememberCoroutineScope()

    Log.d("LibMain", "libraryId = $libraryId")
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
    ) {
        Row {
            LibraryMainTabs(selectedTabIndex = pagerState.currentPage,
                onSelectedTab = {
                    scope.launch {
                        pagerState.animateScrollToPage(it.ordinal)
                    }
                })
        }
        Row {
            HorizontalPager(

                state = pagerState,
            )
            { index ->
                Column(Modifier.fillMaxSize()) {
                    when (index) {
                        0 -> AllSeriesTab(onItemClick = {
                            navigator.navigate(
                                SeriesByIdScreenDestination(seriesId = it)
                            )
                        })
                        1 -> AllReadListTab(onItemClick = {
                            navigator.navigate(
                                ReadListByIdScreenDestination(readListId = it)
                            )
                        })
                        2 -> AllCollectionsTab(onItemClick={navigator.navigate(
                            CollectionByIdScreenDestination(collectionId = it)
                        )})
                    }
                }
            }
        }
    }

}


