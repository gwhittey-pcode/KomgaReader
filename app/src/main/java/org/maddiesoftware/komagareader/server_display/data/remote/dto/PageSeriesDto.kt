package org.maddiesoftware.komagareader.server_display.data.remote.dto

import org.maddiesoftware.komagareader.server_display.domain.model.PageSeries
import org.maddiesoftware.komagareader.server_display.domain.model.Series


data class PageSeriesDto(

    val content: List<SeriesDto>?,
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
    fun toPageSeries(): PageSeries {
        return PageSeries(
            series = content?.map { it.toSeries() },
            empty = empty,
            first = first,
            last = last,
            number = number,
            numberOfElements = numberOfElements,
            pageable = pageable,
            size = size,
//            sort = sort,
            totalElements = totalElements,
            totalPages = totalPages

        )
    }
    fun toListSeries() : List<Series>? {
        return this.content?.map { it.toSeries() }
    }

}
