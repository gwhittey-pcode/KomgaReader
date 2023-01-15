package org.maddiesoftware.komagareader.komga_server.data.remote.dto

import org.maddiesoftware.komagareader.komga_server.domain.model.PageBook

data class PageBookDto(
    val content: List<BookDto>?,
//    val empty: Boolean?,
//    val first: Boolean?,
//    val last: Boolean?,
    val number: Int?,
    val numberOfElements: Int?,
//    val pageable: PageableDto?,
    val size: Int?,
    val sort: SortDtoX?,
    val totalElements: Int?,
    val totalPages: Int?
){
    fun toPageBook(): PageBook{
        return PageBook(
            books = this.content?.map { it.toBook() },
            number = number,
            numberOfElements = numberOfElements,
            size = size,
            totalElements = totalElements,
            totalPages = totalPages


        )
    }
}
