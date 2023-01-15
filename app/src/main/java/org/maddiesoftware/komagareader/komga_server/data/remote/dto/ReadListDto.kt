package org.maddiesoftware.komagareader.komga_server.data.remote.dto

data class ReadListDto(
    val bookIds: List<String?>?,
    val createdDate: String?,
    val filtered: Boolean?,
    val id: String?,
    val lastModifiedDate: String?,
    val name: String?,
    val summary: String?
)
