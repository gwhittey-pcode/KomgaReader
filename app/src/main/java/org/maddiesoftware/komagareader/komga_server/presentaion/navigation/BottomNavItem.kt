package org.maddiesoftware.komagareader.komga_server.presentaion.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.ui.graphics.vector.ImageVector
import org.maddiesoftware.komagareader.R

enum class BottomNavItem(
    val type: String,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Series("Series", Icons.Default.Home, R.string.series_screen),
    ReadList("Read List",Icons.Default.ViewList,R.string.read_list_screen),
    Collections("Collections",Icons.Default.ViewList,R.string.collections_screen)
}