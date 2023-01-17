package org.maddiesoftware.komagareader.core.presentation.componets

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.CoroutineScope
import org.maddiesoftware.komagareader.destinations.Destination

@Composable
fun MainTopBar(
    onNavigationIconClick: () -> Unit,
    onMenuItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    destination: Destination,
    navBackStackEntry: NavBackStackEntry?,


    ) {
    TopAppBar(
        modifier = modifier,
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