package org.maddiesoftware.komagareader.komga_server.data.remote.dto

import org.maddiesoftware.komagareader.komga_server.domain.model.Page

data class PageDto(
    val fileName: String,
    val height: Int?,
    val mediaType: String,
    val number: Int,
    val size: String,
    val sizeBytes: Int?,
    val width: Int?
){
    fun toPage( ):Page{
        return Page(
            fileName = fileName,
            height = height,
            mediaType = mediaType,
            number = number,
            size = size,
            sizeBytes = sizeBytes,
            width = width,

        )
    }
}
