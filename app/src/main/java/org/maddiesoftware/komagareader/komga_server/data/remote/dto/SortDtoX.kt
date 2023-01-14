package org.maddiesoftware.komagareader.komga_server.data.remote.dto

import org.maddiesoftware.komagareader.komga_server.domain.model.SortX


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
