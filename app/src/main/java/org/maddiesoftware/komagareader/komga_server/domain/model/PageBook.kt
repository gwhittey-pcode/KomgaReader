package org.maddiesoftware.komagareader.komga_server.domain.model

data class PageBook(
    val books: List<Book>?,
    val number: Int?,
    val numberOfElements: Int?,
    val size: Int?,
    val totalElements: Int?,
    val totalPages: Int?
)