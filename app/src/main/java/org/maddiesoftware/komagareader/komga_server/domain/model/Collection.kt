package org.maddiesoftware.komagareader.komga_server.domain.model

data class Collection(
    val createdDate: String?,
    val filtered: Boolean?,
    val id: String?,
    val lastModifiedDate: String?,
    val name: String?,
    val ordered: Boolean?,
    val seriesIds: List<String?>?
)
