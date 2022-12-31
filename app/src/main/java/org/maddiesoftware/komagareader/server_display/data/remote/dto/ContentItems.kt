package org.maddiesoftware.komagareader.server_display.data.remote.dto

import com.squareup.moshi.Json

/**
 * This structure represent a List of items.
 * This structure can be used across multiple feature modules or any other modules.
 */
data class ResponseItems<T>(
    @field:Json(name = "content") val content: List<T>
)