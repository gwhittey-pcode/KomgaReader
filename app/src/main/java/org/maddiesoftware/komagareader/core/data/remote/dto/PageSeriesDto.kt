package org.maddiesoftware.komagareader.core.data.remote.dto

import com.squareup.moshi.Json
import org.maddiesoftware.komgareader.data.local.komgaserver.entity.PageSeriesEntity
package org.maddiesoftware.komagareader.core.domain.model.PageSeries

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
    fun toPageSeries(): PageSeries{
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

}
