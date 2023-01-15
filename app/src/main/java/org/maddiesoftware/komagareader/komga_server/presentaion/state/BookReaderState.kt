package org.maddiesoftware.komagareader.komga_server.presentaion.state

import org.maddiesoftware.komagareader.komga_server.domain.BookPage
import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.model.Page

data class BookReaderState(
    val bookInfo: Book? = null,
    val pagesInfo: List<Page>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val pagerPages: List<BookPage>? = emptyList(),
    val useDblPageSplit:Boolean = false

)


