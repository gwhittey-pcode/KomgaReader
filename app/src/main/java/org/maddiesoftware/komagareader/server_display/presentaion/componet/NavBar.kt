package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable

@Composable
fun NavBar(
    onNavigationIconClick: () -> Unit,
    onMenuItemClick: () -> Unit
) {
    TopAppBar(
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
        }}
    )
}