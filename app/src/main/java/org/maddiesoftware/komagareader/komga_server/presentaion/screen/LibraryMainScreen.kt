package org.maddiesoftware.komagareader.komga_server.presentaion.screen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.destinations.*
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.NavBar
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.NavDrawer
import org.maddiesoftware.komagareader.komga_server.presentaion.navigation.BottomBar
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
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomBar(
                onItemClick = {
                    when(it){
                        "Series" -> {navigator.navigate(AllSeriesScreenDestination(libraryId = libraryId))}
                        "Read List" -> {navigator.navigate(AllReadListScreenDestination(libraryId = libraryId))}
                        "Collections" -> {}
                    }
                }
            )
        },
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
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    when (id) {
                        "home" -> {
                            navigator.navigate(HomeScreenDestination())
                        }
                        "settings" -> {
                            navigator.navigate(SettingsScreenDestination())
                        }
                        else -> {
                            navigator.navigate(LibraryMainScreenDestination(libraryId = id))
                        }
                    }
                }
            )
        }
    ) {
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
}