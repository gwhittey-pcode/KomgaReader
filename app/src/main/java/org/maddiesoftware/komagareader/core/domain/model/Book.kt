package org.maddiesoftware.komagareader.core.domain.model

data class Book(
    val created: String?,
    val deleted: Boolean?,
    val fileHash: String?,
    val fileLastModified: String?,
    val id: String?,
    val lastModified: String?,
    val libraryId: String?,
    val media: org.maddiesoftware.komgareader.domain.model.Media?,
    val metadata: org.maddiesoftware.komgareader.domain.model.BookMetadata?,
    val name: String?,
    val number: Int?,
    val readProgress: org.maddiesoftware.komgareader.domain.model.ReadProgress?,
    val seriesId: String?,
    val seriesTitle: String?,
    val size: String?,
    val sizeBytes: Int?,
    val url: String?
)
