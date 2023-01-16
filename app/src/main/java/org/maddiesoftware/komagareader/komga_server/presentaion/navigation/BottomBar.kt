package org.maddiesoftware.komagareader.komga_server.presentaion.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BottomBar(
    onItemClick: (id: String) -> Unit,
) {


    BottomNavigation {
        BottomNavItem.values().forEach {
            BottomNavigationItem(
                selected = false, onClick = { onItemClick(it.type) },
                icon = { Icon(it.icon, contentDescription = stringResource(it.label)) },
                label = { Text(stringResource(it.label)) },
            )
        }
    }
}