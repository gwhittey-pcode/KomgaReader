package org.maddiesoftware.komagareader.core.data.remote.dto

package org.maddiesoftware.komagareader.core.domain.model.SeriesMetadata

data class SeriesMetadataDto(
    val ageRating: Int?,
    val ageRatingLock: Boolean?,
    val created: String?,
    val genres: List<String?>?,
    val genresLock: Boolean?,
    val language: String?,
    val languageLock: Boolean?,
    val lastModified: String?,
    val publisher: String?,
    val publisherLock: Boolean?,
    val readingDirection: String?,
    val readingDirectionLock: Boolean?,
    val status: String?,
    val statusLock: Boolean?,
    val summary: String?,
    val summaryLock: Boolean?,
    val tags: List<String?>?,
    val tagsLock: Boolean?,
    val title: String?,
    val titleLock: Boolean?,
    val titleSort: String?,
    val titleSortLock: Boolean?,
    val totalBookCount: Int?,
    val totalBookCountLock: Boolean?
){
    fun toSeriesMetadata(): SeriesMetadata{
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
