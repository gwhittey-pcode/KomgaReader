package org.maddiesoftware.komagareader.komga_server.domain.model

data class ReadList(
    val bookIds: List<String?>?,
    val createdDate: String?,
    val filtered: Boolean?,
    val id: String?,
    val lastModifiedDate: String?,
    val name: String?,
    val summary: String?
)
