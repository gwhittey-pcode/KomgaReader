package org.maddiesoftware.komagareader.komga_server.presentaion.state

import org.maddiesoftware.komagareader.komga_server.domain.model.Book

data class BookInfoState(
    val bookInfo: Book? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
//    var hasNextBook: Boolean = false,
    val nextBookInfo:  Book? = null,
//    var hasPrevBook: Boolean = false,
    val prevBookInfo: Book? = null,
)
