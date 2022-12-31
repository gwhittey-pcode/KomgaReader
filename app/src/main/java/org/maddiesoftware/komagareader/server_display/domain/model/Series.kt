package org.maddiesoftware.komagareader.server_display.domain.model

import org.maddiesoftware.komagareader.server_display.data.remote.dto.BookMetadataAggregationDto
import org.maddiesoftware.komagareader.server_display.data.remote.dto.SeriesMetadataDto


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
