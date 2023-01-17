package org.maddiesoftware.komagareader.core.presentation.componets

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.spec.Route
import kotlinx.coroutines.CoroutineScope
import org.maddiesoftware.komagareader.appCurrentDestinationAsState
import org.maddiesoftware.komagareader.destinations.BookReaderScreenDestination
import org.maddiesoftware.komagareader.destinations.Destination
import org.maddiesoftware.komagareader.startAppDestination

@OptIn(ExperimentalMaterialNavigationApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun MainScaffold(
    scaffoldState:ScaffoldState,
    scopeState:CoroutineScope,
    startRoute: Route,
    navController: NavHostController,
    topBar: @Composable (Destination, NavBackStackEntry?) -> Unit,
//    bottomBar: @Composable (Destination) -> Unit,
    navDrawer: @Composable (Destination) -> Unit,
    content: @Composable (PaddingValues) -> Unit,

    ){
    val destination = navController.appCurrentDestinationAsState().value
        ?: startRoute.startAppDestination
    val navBackStackEntry = navController.currentBackStackEntry

    // 👇 only for debugging, you shouldn't use currentBackStack API as it is restricted by annotation
//    navController.currentBackStack.collectAsState().value.print()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(16.dp)
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { topBar(destination, navBackStackEntry) },
//            bottomBar = { bottomBar(destination) },
            drawerContent = { navDrawer( destination ) },
            content = content
        )
    }
}
private val Destination.hideTopBar get() = this !is BookReaderScreenDestination


//drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
//drawerContent = {
//    NavDrawer(
//        libraryList = libraryList,
//        onItemClick = { id ->
//            scope.launch {
//                scaffoldState.drawerState.close()
//            }
//            when (id) {
//                "home" -> {
//                    navigator.navigate(HomeScreenDestination())
//                }
//                "settings" -> {
//                    navigator.navigate(SettingsScreenDestination())
//                }
//                else -> {
//                    navigator.navigate(AllSeriesScreenDestination(libraryId = id))
//                }
//            }
//        }
//    )
//}