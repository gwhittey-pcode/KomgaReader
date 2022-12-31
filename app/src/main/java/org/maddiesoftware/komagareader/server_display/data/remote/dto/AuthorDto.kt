package org.maddiesoftware.komagareader.server_display.data.remote.dto

import org.maddiesoftware.komagareader.server_display.domain.model.Author


data class AuthorDto(
    val name: String?,
    val role: String?
){
    fun toAuthor(): org.maddiesoftware.komagareader.server_display.domain.model.Author {
        return org.maddiesoftware.komagareader.server_display.domain.model.Author(
            name = name,
            role = role
        )
    }
}
