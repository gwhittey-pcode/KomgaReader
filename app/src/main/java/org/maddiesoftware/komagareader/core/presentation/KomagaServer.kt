package org.maddiesoftware.komagareader.core.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.NavGraphs
import org.maddiesoftware.komagareader.core.presentation.componets.MainScaffold
import org.maddiesoftware.komagareader.core.presentation.componets.MainTopBar
import org.maddiesoftware.komagareader.destinations.Destination
import org.maddiesoftware.komagareader.destinations.ServerSelectScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.general.NavDrawer

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun KomgaServer() {
    val engine = rememberAnimatedNavHostEngine()
    val navController = engine.rememberNavController()
    val startRoute = NavGraphs.root.startRoute
    val scaffoldState = rememberScaffoldState()
    val scopeState = rememberCoroutineScope()
    MainScaffold(
        navController = navController,
        startRoute = startRoute,
        scaffoldState = scaffoldState,
        scopeState = scopeState,
        topBar = { dest, backStackEntry ->
            if (dest.shouldShowScaffoldElements) {
                MainTopBar(
                    onMenuItemClick = {
                        scopeState.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                    onNavigationIconClick = {navController.navigateUp() },
                    modifier = Modifier,
                    destination = dest,
                    navBackStackEntry = backStackEntry,

                )
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
                startRoute = startRoute
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

private val Destination.shouldShowScaffoldElements get() = this !is ServerSelectScreenDestination


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