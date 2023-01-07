package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.runtime.Composable
import org.maddiesoftware.komagareader.R
import org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid.tabs.BooksTab
import org.maddiesoftware.komagareader.server_display.presentaion.screen.seriesbyid.tabs.SeriesTabInfo


typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var icon: Int, var title: String, var screen: ComposableFun){
    object Books: TabItem(icon = R.drawable.ic_books, title = "Books", screen = {BooksTab()})
    object SeriesInfo: TabItem(icon = R.drawable.ic_info, title = "Info", screen = {SeriesTabInfo()})
}
