package org.maddiesoftware.komagareader.komga_server.data.remote.dto


data class AuthorDto(
    val name: String?,
    val role: String?
){
    fun toAuthor(): org.maddiesoftware.komagareader.komga_server.domain.model.Author {
        return org.maddiesoftware.komagareader.komga_server.domain.model.Author(
            name = name,
            role = role
        )
    }
}
