package org.maddiesoftware.komagareader.komga_server.domain.model

data class Pageable(
    val offset: Int?,
    val pageNumber: Int?,
    val pageSize: Int?,
    val paged: Boolean?,
    val unpaged: Boolean?
)
