package org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader

import org.maddiesoftware.komagareader.server_display.domain.model.Book
import org.maddiesoftware.komagareader.server_display.domain.model.Page
import org.maddiesoftware.komagareader.server_display.presentaion.screen.bookreader.util.BookPage

data class BookReaderState(
    val bookInfo: Book? = null,
    val pagesInfo: List<Page>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val pagerPages: List<BookPage>? = emptyList(),
    val useDblPageSplit:Boolean = false

)


