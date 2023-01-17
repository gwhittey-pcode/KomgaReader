package org.maddiesoftware.komagareader.core.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import org.maddiesoftware.komagareader.NavGraphs
import org.maddiesoftware.komagareader.core.presentation.componets.MainScaffold
import org.maddiesoftware.komagareader.core.presentation.componets.MainTopBar
import org.maddiesoftware.komagareader.destinations.Destination
import org.maddiesoftware.komagareader.destinations.ServerSelectScreenDestination

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun KomgaServer(){
    val engine = rememberAnimatedNavHostEngine()
    val navController = engine.rememberNavController()
    val startRoute = NavGraphs.root.startRoute

    MainScaffold(
        navController = navController,
        startRoute = startRoute,
        topBar = { dest, backStackEntry ->
            if (dest.shouldShowScaffoldElements) {
                MainTopBar(
                    onMenuItemClick = {},
                    onNavigationIconClick = {},
                    modifier = Modifier,
                    destination = dest,
                    navBackStackEntry = backStackEntry
                )
            }
        },
//        bottomBar = {
//            if (it.shouldShowScaffoldElements) {
//                BottomBar(navController)
//            }
//        }
    ) {
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
}
private val Destination.shouldShowScaffoldElements get() = this !is ServerSelectScreenDestination