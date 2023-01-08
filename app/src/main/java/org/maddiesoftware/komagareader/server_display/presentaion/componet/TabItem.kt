package org.maddiesoftware.komagareader.server_display.presentaion.componet

import androidx.compose.runtime.Composable


typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(
    var icon: Int,
    var title: String,
    var screen: ComposableFun,
    onItemClick: (id: String) -> Unit,
    ){

//    object Books: TabItem(icon = R.drawable.ic_books, title = "Books", screen = { BooksTab() })
//    object SeriesInfo: TabItem(icon = R.drawable.ic_info, title = "Info", screen = {SeriesTabInfo()})
}
