package org.maddiesoftware.komagareader.server_display.data.remote.dto

import org.maddiesoftware.komagareader.server_display.domain.model.Page

data class PageDto(
    val fileName: String?,
    val height: Int?,
    val mediaType: String?,
    val number: Int?,
    val size: String?,
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
