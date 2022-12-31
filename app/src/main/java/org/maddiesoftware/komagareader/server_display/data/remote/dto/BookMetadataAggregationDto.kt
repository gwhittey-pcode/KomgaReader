package org.maddiesoftware.komagareader.server_display.data.remote.dto

import org.maddiesoftware.komagareader.server_display.data.remote.dto.AuthorDto
import org.maddiesoftware.komagareader.server_display.domain.model.BookMetadataAggregation



data class BookMetadataAggregationDto(
    val authors: List<AuthorDto?>?,
    val created: String?,
    val lastModified: String?,
    val releaseDate: String?,
    val summary: String?,
    val summaryNumber: String?,
    val tags: List<String?>?
){
    fun toBookMetadataAggregation(): BookMetadataAggregation {
        return BookMetadataAggregation(
            authors = authors?.map { it?.toAuthor() },
            created = created,
            lastModified = lastModified,
            releaseDate = releaseDate,
            summary = summary,
            summaryNumber = summaryNumber,
            tags = tags
        )
    }
}
