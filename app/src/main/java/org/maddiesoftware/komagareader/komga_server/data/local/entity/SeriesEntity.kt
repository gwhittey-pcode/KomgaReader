package org.maddiesoftware.komagareader.komga_server.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.maddiesoftware.komagareader.core.util.SERIES_TABLE
import org.maddiesoftware.komagareader.komga_server.data.remote.dto.BookMetadataAggregationDto
import org.maddiesoftware.komagareader.komga_server.data.remote.dto.SeriesMetadataDto


@Entity(tableName = SERIES_TABLE)
data class SeriesEntity(
    @PrimaryKey val id: String,
    val booksCount: Int?,
    val booksInProgressCount: Int?,
    val booksMetadata: BookMetadataAggregationDto?,
    val booksReadCount: Int?,
    val booksUnreadCount: Int?,
    val created: String?,
    val deleted: Boolean?,
    val fileLastModified: String?,
    val lastModified: String?,
    val libraryId: String?,
    val metadata: SeriesMetadataDto?,
    val name: String,
    val url: String?

    ) {

}