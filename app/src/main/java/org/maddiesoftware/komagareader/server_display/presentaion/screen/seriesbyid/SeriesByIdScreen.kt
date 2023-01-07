package org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.server_display.presentaion.activity.MainViewModule
import org.maddiesoftware.komagareader.server_display.presentaion.componet.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Destination
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SeriesByIdScreen(
    seriesId: String? = null,
    navigator: DestinationsNavigator,
    mainViewModule: MainViewModule = hiltViewModel(),
){

    val libraryList = mainViewModule.state.libraryList
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val tabs = listOf(
        TabItem.Books,
        TabItem.SeriesInfo,

    )
    val pagerState = rememberPagerState(pageCount = tabs.size)


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            NavBar(
                onNavigationIconClick = {navigator.navigateUp()},
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
                libraryList=libraryList,
                viewModel=mainViewModule,
                onItemClick = {id ->
                    if (id == "home"){
                        navigator.navigate(HomeScreenDestination())
                    }else {
                        navigator.navigate(AllSeriesScreenDestination(libraryId = id))
                    }
                }
            )
        }
    ) {

        Column() {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
}