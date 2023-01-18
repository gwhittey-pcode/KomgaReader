package org.maddiesoftware.komagareader.komga_server.presentaion.componet.general

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.Destination

@Composable
fun NavBar(
    onNavigationIconClick: () -> Unit,
    onMenuItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    destination: Destination,
    navBackStackEntry: NavBackStackEntry?,
    mainViewModel: MainViewModel
) {
    val topBarHeight = 56.dp
    Log.d("showTopBar","mainState.showTopBar=${mainViewModel.showTopBar.value}")
    TopAppBar(
        modifier = Modifier.height(height = if (!mainViewModel.showTopBar.value) 0.dp else topBarHeight),
        title = {
            Text(text = "KomgaReader")
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

        },
        actions = {
            IconButton(onClick = onMenuItemClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer"
                )
            }
        }
    )
}