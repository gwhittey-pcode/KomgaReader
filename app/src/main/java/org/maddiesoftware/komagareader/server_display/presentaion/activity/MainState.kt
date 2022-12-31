package org.maddiesoftware.komagareader.server_display.presentaion.activity

import org.maddiesoftware.komagareader.server_display.domain.model.Library

data class MainState(
    val libraryList: List<Library>? =null,
    val isLoading: Boolean = false,
    val error: String? = null
)
