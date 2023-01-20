package org.maddiesoftware.komagareader.komga_server.presentaion.componet.general

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.maddiesoftware.komagareader.NavGraphs
import org.maddiesoftware.komagareader.destinations.AllCollectionsScreenDestination
import org.maddiesoftware.komagareader.destinations.AllSeriesScreenDestination
import org.maddiesoftware.komagareader.destinations.HomeScreenDestination
import org.maddiesoftware.komagareader.destinations.SettingsScreenDestination
import org.maddiesoftware.komagareader.komga_server.presentaion.viewmodels.LibraryViewModule

@Composable
fun NavDrawer(
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    navController: NavController,
    scaffoldState: ScaffoldState,
    scopeState: CoroutineScope,

) {
    DrawerHeader()
    DrawerBodySelectionScreen(
        modifier = modifier,
        itemTextStyle = itemTextStyle,
        navController=navController,
        scaffoldState=scaffoldState,
        scopeState=scopeState,
    )

}

@Composable
fun DrawerHeader(
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Header",
            fontSize = 24.sp,
            color = MaterialTheme.colors.onSecondary,
        )
    }
}

@Composable
fun DrawerBodySelectionScreen(
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    navController: NavController,
    scaffoldState: ScaffoldState,
    scopeState: CoroutineScope,

    ) {
    val libraryViewModule: LibraryViewModule = hiltViewModel()
    val libraryList = libraryViewModule.state.libraryList
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    scopeState.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        // Restore state when reselecting a previously selected item
                        .navigate(AllCollectionsScreenDestination(libraryId = "09YPSVS759MM2")) {
                            // Pop up to the root of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(NavGraphs.root) {
                                saveState = true
                            }

                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                }
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Collections",
                tint = MaterialTheme.colors.onSecondary,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Collections",
                style = itemTextStyle,
                modifier = Modifier.weight(1f),
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.primary,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    scopeState.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        // Restore state when reselecting a previously selected item
                        .navigate(HomeScreenDestination) {
                            // Pop up to the root of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(NavGraphs.root) {
                                saveState = true
                            }

                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                }
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                tint = MaterialTheme.colors.onSecondary,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Home",
                style = itemTextStyle,
                modifier = Modifier.weight(1f),
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.primary,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    scopeState.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        // Restore state when reselecting a previously selected item
                        .navigate(SettingsScreenDestination) {
                            // Pop up to the root of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(NavGraphs.root) {
                                saveState = true
                            }

                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                }
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colors.onSecondary,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Settings",
                style = itemTextStyle,
                modifier = Modifier.weight(1f),
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colors.primary,
            )
        }
        Row(){
            NavDrawerExpandCard(
                title = "Libraries",
                libraryList = libraryList,
                onItemClick = {
                    scopeState.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        // Restore state when reselecting a previously selected item
                        .navigate(AllSeriesScreenDestination(libraryId = it)) {
                            // Pop up to the root of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(NavGraphs.root) {
                                saveState = true
                            }

                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                }
            )
        }


    }

}

