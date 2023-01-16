package org.maddiesoftware.komagareader.komga_server.data.remote.dto

import org.maddiesoftware.komagareader.komga_server.domain.model.ReadList

data class ReadListDto(
    val bookIds: List<String?>?,
    val createdDate: String?,
    val filtered: Boolean?,
    val id: String?,
    val lastModifiedDate: String?,
    val name: String?,
    val summary: String?
){
    fun toReadList():ReadList{
        return ReadList(
            bookIds=bookIds,
            createdDate=createdDate,
            filtered=filtered,
            id=id,
            lastModifiedDate=lastModifiedDate,
            name = name,
            summary =summary
        )
    }
}
