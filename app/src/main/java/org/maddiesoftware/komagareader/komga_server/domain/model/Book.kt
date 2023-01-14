package org.maddiesoftware.komagareader.komga_server.domain.model

import org.maddiesoftware.komagareader.komga_server.data.remote.dto.BookMetadataDto
import org.maddiesoftware.komagareader.komga_server.data.remote.dto.MediaDto
import org.maddiesoftware.komagareader.komga_server.data.remote.dto.ReadProgressDto

data class Book(
    val created: String?,
    val deleted: Boolean?,
    val id: String?,
//    val lastModified: String?,
    val libraryId: String?,
    val media: MediaDto?,
    val metadata: BookMetadataDto?,
    val name: String?,
    val number: Int?,
    val readProgress: ReadProgressDto?,
    val seriesId: String?,
    val seriesTitle: String?,
    val size: String?,
//    val sizeBytes: Int?,
//    val url: String?
)
