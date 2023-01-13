package org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.BookInfoScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.destinations.SettingsScreenDestination
import org.maddiesoftware.komagareader.server_display.presentaion.activity.MainViewModule
import org.maddiesoftware.komagareader.server_display.presentaion.componet.*
import org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid.tabs.BooksTab
import org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid.tabs.SeriesTabInfo


@OptIn(ExperimentalPagerApi::class)
@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SeriesByIdScreen(
    seriesId: String? = null,
    navigator: DestinationsNavigator,
    mainViewModule: MainViewModule = hiltViewModel(),
) {
    val libraryList = mainViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            NavBar(

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
                onItemClick = { id ->
                    when(id){
                        "home" -> {navigator.navigate(HomeScreenDestination())}
                        "settings" -> {navigator.navigate(SettingsScreenDestination())}
                        else -> {navigator.navigate(AllSeriesScreenDestination(libraryId = id))}
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxSize()
        ) {
            Row {
                SeriesByIdTabs(selectedTabIndex = pagerState.currentPage,
                    onSelectedTav = {
                        scope.launch {
                            pagerState.animateScrollToPage(it.ordinal)
                        }
                    })
            }
            Row {
                HorizontalPager(count = SeriesByIdTabPage.values().size, state = pagerState) { index ->
                    Column(Modifier.fillMaxSize()) {
                        when (index) {
                            0 -> BooksTab(
                                onItemClick = {
                                    navigator.navigate(BookInfoScreenDestination(bookId = it))
                                }
                            )
                            1 -> SeriesTabInfo()
                        }
                    }
                }
            }
        }
    }
}

