package org.maddiesoftware.komagareader.core.data.remote.dto

import org.maddiesoftware.komagareader.core.data.remote.dto.AuthorDto
package org.maddiesoftware.komagareader.core.domain.model.BookMetadataAggregation

data class BookMetadataAggregationDto(
    val authors: List<AuthorDto?>?,
    val created: String?,
    val lastModified: String?,
    val releaseDate: String?,
    val summary: String?,
    val summaryNumber: String?,
    val tags: List<String?>?
){
    fun toBookMetadataAggregation(): BookMetadataAggregation{
        return BookMetadataAggregation(
            authors = authors?.map { it?.toAuthor() },
            created= created,
            lastModified = lastModified,
            releaseDate = releaseDate,
            summary = summary,
            summaryNumber = summaryNumber,
            tags = tags
        )
    }
}
