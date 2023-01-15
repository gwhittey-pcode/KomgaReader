package org.maddiesoftware.komagareader.komga_server.domain.model

import org.maddiesoftware.komagareader.komga_server.data.remote.dto.PageableDto


data class PageSeries(
    val series: List<Series>?,
    val empty: Boolean?,
    val first: Boolean?,
    val last: Boolean?,
    val number: Int?,
    val numberOfElements: Int?,
    val pageable: PageableDto?,
    val size: Int?,
//    val sort: SortDtoX?,
    val totalElements: Int?,
    val totalPages: Int?
){

}
