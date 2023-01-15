package org.maddiesoftware.komagareader.komga_server.domain.model

data class ReadProgress(
    val completed: Boolean?,
    val created: String?,
    val lastModified: String?,
    val page: Int?,
    val readDate: String?
)
