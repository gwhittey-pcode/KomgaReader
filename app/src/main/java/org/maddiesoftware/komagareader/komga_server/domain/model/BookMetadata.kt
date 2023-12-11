package org.maddiesoftware.komagareader.komga_server.domain.model



data class BookMetadata(
    val authors: List<org.maddiesoftware.komagareader.komga_server.domain.model.Author?>?,
    val authorsLock: Boolean?,
    val created: String?,
    val isbn: String?,
    val isbnLock: Boolean?,
    val lastModified: String?,
    val links: List<org.maddiesoftware.komagareader.komga_server.domain.model.Link?>?,
    val linksLock: Boolean?,
    val number: String?,
    val numberLock: Boolean?,
    val numberSort: Float?,
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
