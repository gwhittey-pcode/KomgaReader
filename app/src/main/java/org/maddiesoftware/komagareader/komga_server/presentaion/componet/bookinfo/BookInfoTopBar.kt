package org.maddiesoftware.komagareader.komga_server.presentaion.componet.bookinfo

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import org.maddiesoftware.komagareader.core.presentation.viewmodels.MainViewModel
import org.maddiesoftware.komagareader.destinations.Destination

@Composable
fun BookInfoTopBar(
    onNavigationIconClick: () -> Unit,
    onMenuItemClick: () -> Unit,
//    onNextIconClick: () -> Unit,
//    onPrevIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    destination: Destination,
    navBackStackEntry: NavBackStackEntry?,
    mainViewModel: MainViewModel,
    navController:NavController
) {
    val topBarHeight = 56.dp
    TopAppBar(
        modifier = Modifier
            .height(height = if (!mainViewModel.showTopBar.value) 0.dp else topBarHeight),
        title = {
            Text(text = " This" + mainViewModel.topBarTitle.value, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
                    contentDescription = "Toggle drawer",
                )
            }
        }
    )
}