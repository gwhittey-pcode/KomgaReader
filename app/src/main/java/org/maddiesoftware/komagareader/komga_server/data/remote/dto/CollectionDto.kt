package org.maddiesoftware.komagareader.komga_server.data.remote.dto
import org.maddiesoftware.komagareader.komga_server.domain.model.CollectionX
data class CollectionDto(
    val createdDate: String?,
    val filtered: Boolean?,
    val id: String?,
    val lastModifiedDate: String?,
    val name: String?,
    val ordered: Boolean?,
    val seriesIds: List<String?>?
){
    fun toCollection(): CollectionX {
        return CollectionX(
            id = id,
            name = name,
            seriesIds = seriesIds
        )
    }
}
