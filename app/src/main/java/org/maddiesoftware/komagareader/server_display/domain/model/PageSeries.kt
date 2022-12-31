package org.maddiesoftware.komagareader.server_display.domain.model

import org.maddiesoftware.komagareader.server_display.data.remote.dto.PageableDto


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
