package org.maddiesoftware.komagareader.server_display.domain.model

data class ReadProgress(
    val completed: Boolean?,
    val created: String?,
    val lastModified: String?,
    val page: Int?,
    val readDate: String?
)
