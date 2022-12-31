package org.maddiesoftware.komagareader.server_display.data.remote.dto

import org.maddiesoftware.komagareader.server_display.domain.model.SeriesMetadata


data class SeriesMetadataDto(
    val ageRating: Int?,
    val created: String?,
    val genres: List<String?>?,
    val language: String?,
    val lastModified: String?,
    val publisher: String?,
    val readingDirection: String?,
    val status: String?,
    val summary: String?,
    val tags: List<String?>?,
    val title: String?,
    val titleSort: String?,
    val totalBookCount: Int?,
){
    fun toSeriesMetadata(): SeriesMetadata {
        return SeriesMetadata(
            lastModified = lastModified,
            publisher = publisher,
            status = status,
            summary = summary,
            tags = tags,
            title = title,
            totalBookCount = totalBookCount,
            readingDirection = readingDirection,
            created = created,
            genres = genres

        )
    }
}
