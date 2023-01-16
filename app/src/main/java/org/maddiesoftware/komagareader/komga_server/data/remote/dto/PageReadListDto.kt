package org.maddiesoftware.komagareader.komga_server.data.remote.dto

import org.maddiesoftware.komagareader.komga_server.domain.model.PageReadList

data class PageReadListDto(
    val content: List<ReadListDto>?,
    val empty: Boolean?,
    val first: Boolean?,
    val last: Boolean?,
    val number: Int?,
    val numberOfElements: Int?,
    val pageable: PageableDto?,
    val size: Int?,
    val sort: SortDtoX?,
    val totalElements: Int?,
    val totalPages: Int?
){
    fun toPageReadList(): PageReadList{
        return PageReadList(
            readlists = content?.map { it.toReadList() }
        )
    }
}
