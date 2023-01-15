package org.maddiesoftware.komagareader.komga_server.domain.model

data class PageCollection(
    val content: List<org.maddiesoftware.komagareader.komga_server.domain.model.Collection>?,
    val empty: Boolean?,
    val first: Boolean?,
    val last: Boolean?,
    val number: Int?,
    val numberOfElements: Int?,
    val pageable: Pageable?,
    val size: Int?,
    val sort: SortX?,
    val totalElements: Int?,
    val totalPages: Int?
)
