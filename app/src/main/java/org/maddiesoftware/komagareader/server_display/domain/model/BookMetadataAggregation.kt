package org.maddiesoftware.komagareader.server_display.domain.model

import org.maddiesoftware.komagareader.server_display.domain.model.Author

data class BookMetadataAggregation(
    val authors: List<org.maddiesoftware.komagareader.server_display.domain.model.Author?>?,
    val created: String?,
    val lastModified: String?,
    val releaseDate: String?,
    val summary: String?,
    val summaryNumber: String?,
    val tags: List<String?>?
)
