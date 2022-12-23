package org.maddiesoftware.komagareader.core.data.remote.dto

data class BookDto(
    val created: String?,
    val deleted: Boolean?,
    val fileHash: String?,
    val fileLastModified: String?,
    val id: String?,
    val lastModified: String?,
    val libraryId: String?,
    val media: MediaDto?,
    val metadata: BookMetadataDto?,
    val name: String?,
    val number: Int?,
    val readProgress: ReadProgressDto?,
    val seriesId: String?,
    val seriesTitle: String?,
    val size: String?,
    val sizeBytes: Int?,
    val url: String?
)
