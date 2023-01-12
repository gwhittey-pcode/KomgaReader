package org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.componets

import android.annotation.SuppressLint
import android.view.Gravity
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.core.data.local.ServerInfoSingleton.libraryList
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.server_display.presentaion.activity.MainViewModule
import org.maddiesoftware.komagareader.server_display.presentaion.componet.NavBar
import org.maddiesoftware.komagareader.server_display.presentaion.componet.NavDrawer

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BookReaderTopBarDialog(
    setShowDialog: (Boolean) -> Unit,
    screenWidth: Dp,
    onItemClick: (id: String) -> Unit,
    navigator: DestinationsNavigator,
) {
    val mainViewModule: MainViewModule = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DestinationStyle.Dialog.properties.let {
            DialogProperties(
                dismissOnBackPress = it.dismissOnBackPress,
                dismissOnClickOutside = it.dismissOnClickOutside,
                securePolicy = it.securePolicy,
                usePlatformDefaultWidth = false
            )
        },
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.TOP)
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
                    viewModel = mainViewModule,
                    onItemClick = { id ->
                        if (id == "home") {
                            navigator.navigate(HomeScreenDestination())
                        } else {
                            navigator.navigate(AllSeriesScreenDestination(libraryId = id))
                        }
                    }
                )
            }
        ) {
        }

    }

}