package org.maddiesoftware.komagareader.core.data.remote.dto

data class BookMetadataDto(
    val authors: List<org.maddiesoftware.komgareader.data.remote.dto.AuthorDto?>?,
    val authorsLock: Boolean?,
    val created: String?,
    val isbn: String?,
    val isbnLock: Boolean?,
    val lastModified: String?,
    val links: List<org.maddiesoftware.komgareader.data.remote.dto.LinkDto?>?,
    val linksLock: Boolean?,
    val number: String?,
    val numberLock: Boolean?,
    val numberSort: Int?,
    val numberSortLock: Boolean?,
    val releaseDate: String?,
    val releaseDateLock: Boolean?,
    val summary: String?,
    val summaryLock: Boolean?,
    val tags: List<String?>?,
    val tagsLock: Boolean?,
    val title: String?,
    val titleLock: Boolean?
)
