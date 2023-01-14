package org.maddiesoftware.komagareader.komga_server.presentaion.state

import org.maddiesoftware.komagareader.komga_server.domain.model.Book

data class BookInfoState(
    val bookInfo: Book? = null ,
    val isLoading: Boolean = false,
    val error: String? = null
)
