package org.maddiesoftware.komagareader.core.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.NavGraphs
import org.maddiesoftware.komagareader.core.presentation.componets.MainScaffold
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.BookInfoScreenDestination
import org.maddiesoftware.komagareader.destinations.Destination
import org.maddiesoftware.komagareader.destinations.ServerAddScreenDestination
import org.maddiesoftware.komagareader.destinations.ServerSelectScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookinfo.BookInfoTopBar
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.NavBar
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.NavDrawer

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun KomgaReaderMainScreen() {
    val engine = rememberAnimatedNavHostEngine()
    val navController = engine.rememberNavController()
    val startRoute = NavGraphs.root.startRoute
    val scaffoldState = rememberScaffoldState()
    val scopeState = rememberCoroutineScope()
    val mainViewModel: MainViewModel = hiltViewModel()
//    val mainState = mainViewModel.mainState
//    val topBarHeight = 56.dp
//    Log.d("showTopBar","mainState.showTopBar=${mainState.showTopBar}")
    MainScaffold(
        navController = navController,
        startRoute = startRoute,
        scaffoldState = scaffoldState,
        scopeState = scopeState,
        topBar = { dest, backStackEntry ->
            if (dest.shouldShowScaffoldElements) {
                if (dest.useBookInfoNavBar){
                    BookInfoTopBar(
                        onMenuItemClick = {
                            scopeState.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                        onNavigationIconClick = {navController.navigateUp() },
//                    modifier = Modifier.height(height = if (!mainState.showTopBar) 0.dp else topBarHeight),
                        destination = dest,
                        navBackStackEntry = backStackEntry,
                        mainViewModel = mainViewModel,
                        navController = navController,
                    )
                }else{
                    NavBar(
                        onMenuItemClick = {
                            scopeState.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                        onNavigationIconClick = {navController.navigateUp() },
//                    modifier = Modifier.height(height = if (!mainState.showTopBar) 0.dp else topBarHeight),
                        destination = dest,
                        navBackStackEntry = backStackEntry,
                        mainViewModel = mainViewModel
                    )
                }

            }
        },
        navDrawer = {
            if (it.shouldShowScaffoldElements) {
                NavDrawer(
                    navController = navController,
                    scaffoldState = scaffoldState,
                    scopeState = scopeState,
                )
            }
        },
        content = {
            DestinationsNavHost(
                engine = engine,
                navController = navController,
                navGraph = NavGraphs.root,
                modifier = Modifier.padding(it),
                startRoute = startRoute,
                dependenciesContainerBuilder = { //this: DependenciesContainerBuilder<*>
//                    dependency(BookReaderScreenDestination) { mainViewModel }
                    dependency(mainViewModel)

                    }
            )

            // Has to be called after calling DestinationsNavHost because only
            // then does NavController have a graph associated that we need for
            // `appCurrentDestinationAsState` method
//        ShowLoginWhenLoggedOut(vm, navController)
        }
//        bottomBar = {
//            if (it.shouldShowScaffoldElements) {
//                BottomBar(navController)
//            }
//        }

    )
}

private val Destination.shouldShowScaffoldElements get() = this !in listOf(ServerSelectScreenDestination,ServerAddScreenDestination)
private val Destination.useBookInfoNavBar get() = this is (BookInfoScreenDestination)


//onItemClick = { id ->
//    scope.launch {
//        scaffoldState.drawerState.close()
//    }
//    when (id) {
//        "home" -> {
//            navController.navigate(Screen.Home.route)
//        }
//        "settings" -> {
//            navController.navigate(Screen.Settings.route)
//        }
//        "readList" ->{
//            navController.navigate(Screen.AllReadingList.passArgs(libraryId = id))
//        }
//        else -> {
//            navController.navigate(Screen.AllSeries.passArgs(libraryId = id))
//        }
//    }