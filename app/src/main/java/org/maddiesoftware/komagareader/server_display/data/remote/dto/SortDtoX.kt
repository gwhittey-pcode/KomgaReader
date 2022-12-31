package org.maddiesoftware.komagareader.server_display.data.remote.dto

import org.maddiesoftware.komagareader.server_display.domain.model.SortX


data class SortDtoX(
    val empty: Boolean?,
    val sorted: Boolean?,
    val unsorted: Boolean?
){
    fun toSortX(): SortX {
        return SortX(
            empty = empty,
            sorted= sorted,
            unsorted = unsorted

        )
    }
}
