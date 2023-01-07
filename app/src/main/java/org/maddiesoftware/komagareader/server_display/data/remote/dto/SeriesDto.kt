package org.maddiesoftware.komagareader.server_display.data.remote.dto

import org.maddiesoftware.komagareader.server_display.domain.model.Series




data class SeriesDto(
    val booksCount: Int?,
    val booksInProgressCount: Int?,
    val booksMetadata: BookMetadataAggregationDto?,
    val booksReadCount: Int?,
    val booksUnreadCount: Int?,
    val created: String?,
    val deleted: Boolean?,
    val fileLastModified: String?,
    val id: String,
    val lastModified: String?,
    val libraryId: String?,
    val metadata: SeriesMetadataDto?,
    val name: String,
    val url: String?
){
    fun toSeries(): Series {
        return Series(
            booksCount = booksCount,
            booksInProgressCount = booksInProgressCount,
            booksMetadata = booksMetadata,
            booksReadCount = booksReadCount,
            booksUnreadCount = booksUnreadCount,
            created = created,
            deleted = deleted,
            fileLastModified = fileLastModified,
            id = id,
            lastModified = lastModified,
            libraryId = libraryId,
            metadata = metadata,
            name = name,
            url = url
        )
    }

}
