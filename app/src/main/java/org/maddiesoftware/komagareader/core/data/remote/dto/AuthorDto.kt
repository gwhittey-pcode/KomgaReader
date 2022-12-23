package org.maddiesoftware.komagareader.core.data.remote.dto

import org.maddiesoftware.komagareader.core.domain.model.Author


data class AuthorDto(
    val name: String?,
    val role: String?
){
    fun toAuthor(): Author {
        return Author(
            name = name,
            role = role
        )
    }
}
