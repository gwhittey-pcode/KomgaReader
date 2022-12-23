package org.maddiesoftware.komagareader.core.domain.model

import org.maddiesoftware.komagareader.core.domain.model.Author

data class BookMetadataAggregation(
    val authors: List<Author?>?,
    val created: String?,
    val lastModified: String?,
    val releaseDate: String?,
    val summary: String?,
    val summaryNumber: String?,
    val tags: List<String?>?
)
