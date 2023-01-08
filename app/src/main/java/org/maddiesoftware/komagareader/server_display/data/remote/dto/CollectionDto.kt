package org.maddiesoftware.komagareader.server_display.data.remote.dto

data class CollectionDto(
    val createdDate: String?,
    val filtered: Boolean?,
    val id: String?,
    val lastModifiedDate: String?,
    val name: String?,
    val ordered: Boolean?,
    val seriesIds: List<String?>?
)