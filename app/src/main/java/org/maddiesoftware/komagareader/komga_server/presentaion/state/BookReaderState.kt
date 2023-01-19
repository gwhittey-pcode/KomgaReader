package org.maddiesoftware.komagareader.komga_server.presentaion.state

import org.maddiesoftware.komagareader.komga_server.domain.model.Book
import org.maddiesoftware.komagareader.komga_server.domain.model.Page

data class BookReaderState(
    val bookInfo: Book? = null,
    val pagesInfo: List<Page>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val useDblPageSplit:Boolean = false,
    val doingUpdateReadProgress: Boolean = false,
    var startPage: Int = 0,
    val totalPages: Int = 0,
)


