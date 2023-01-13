package org.maddiesoftware.komagareader.preference.presentation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton.libraryList
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.destinations.SettingsScreenDestination
import org.maddiesoftware.komagareader.server_display.presentaion.componet.NavBar
import org.maddiesoftware.komagareader.server_display.presentaion.componet.NavDrawer

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun SettingsScreen(navigator: DestinationsNavigator,){
    val scaffoldState = rememberScaffoldState()
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
    ){

    }

}