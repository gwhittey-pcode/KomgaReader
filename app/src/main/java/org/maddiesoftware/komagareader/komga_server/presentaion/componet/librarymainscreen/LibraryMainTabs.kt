package org.maddiesoftware.komagareader.komga_server.presentaion.componet.librarymainscreen

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import org.maddiesoftware.komagareader.komga_server.presentaion.componet.seriesbyid.SeriesByIdTabIndicator

@Composable
fun LibraryMainTabs(selectedTabIndex: Int, onSelectedTab: (LibraryMainTabPage) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier.background(MaterialTheme.colors.surface),
        indicator = { SeriesByIdTabIndicator(tabPosition = it, index = selectedTabIndex) }
    ) {
        LibraryMainTabPage.values().forEachIndexed { index, tabPage ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onSelectedTab(tabPage) },
                text = { Text(text = tabPage.name, color = MaterialTheme.colors.onSurface) },
                icon = {
                    Icon(
                        painter = painterResource(id = tabPage.icon),
                        contentDescription = ""
                    )
                },
                modifier = Modifier.background(MaterialTheme.colors.surface),
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onSurface
            )
        }
    }
}