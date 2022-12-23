package org.maddiesoftware.komagareader.core.data.remote.dto

package org.maddiesoftware.komagareader.core.domain.model.SortX

data class SortDtoX(
    val empty: Boolean?,
    val sorted: Boolean?,
    val unsorted: Boolean?
){
    fun toSortX(): SortX{
        return SortX(
            empty = empty,
            sorted= sorted,
            unsorted = unsorted

        )
    }
}
