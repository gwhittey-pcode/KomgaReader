package org.maddiesoftware.komagareader.komga_server.domain.model

data class BookMetadataAggregation(
    val authors: List<Author?>?,
    val created: String?,
    val lastModified: String?,
    val releaseDate: String?,
    val summary: String?,
    val summaryNumber: String?,
    val tags: List<String?>?
)
