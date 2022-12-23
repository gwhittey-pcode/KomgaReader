package org.maddiesoftware.komagareader.core.data.remote.dto

data class ReadProgressDto(
    val completed: Boolean?,
    val created: String?,
    val lastModified: String?,
    val page: Int?,
    val readDate: String?
)
