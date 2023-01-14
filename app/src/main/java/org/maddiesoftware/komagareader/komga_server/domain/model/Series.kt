package org.maddiesoftware.komagareader.komga_server.domain.model

import org.maddiesoftware.komagareader.komga_server.data.remote.dto.BookMetadataAggregationDto
import org.maddiesoftware.komagareader.komga_server.data.remote.dto.SeriesMetadataDto

data class Series(
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
)
