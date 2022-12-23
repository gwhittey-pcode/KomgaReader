package org.maddiesoftware.komagareader.core.data.remote.dto

package org.maddiesoftware.komagareader.core.domain.model.Pageable

data class PageableDto(
    val offset: Int?,
    val pageNumber: Int?,
    val pageSize: Int?,
    val paged: Boolean?,
    val sort: SortDtoX?,
    val unpaged: Boolean?
){
    fun toPageable(): Pageable{
        return Pageable(
            offset = offset,
            pageNumber = pageNumber,
            pageSize = pageSize,
            paged = paged,
            unpaged = unpaged,

        )

    }
}
