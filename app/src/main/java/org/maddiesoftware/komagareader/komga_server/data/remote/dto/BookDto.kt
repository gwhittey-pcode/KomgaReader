package org.maddiesoftware.komagareader.komga_server.data.remote.dto

import org.maddiesoftware.komagareader.komga_server.domain.model.Book

data class BookDto(
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
){
    fun toBook(): Book{
        return Book(
            created, deleted, id, libraryId, media, metadata, name, number, readProgress, seriesId, seriesTitle, size
        )
    }
}
