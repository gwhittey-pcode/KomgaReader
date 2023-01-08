package org.maddiesoftware.komagareader.server_display.presentaion.screen.bookinfo

import org.maddiesoftware.komagareader.server_display.domain.model.Book

data class BookInfoState(
    val bookInfo: Book? = null ,
    val isLoading: Boolean = false,
    val error: String? = null
)
